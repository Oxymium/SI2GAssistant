package com.oxymium.si2gassistant.ui.scenes.submitperson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.currentUser
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.domain.states.SubmitPersonState
import com.oxymium.si2gassistant.loadingInMillis
import com.oxymium.si2gassistant.domain.validators.SubmitPersonValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SubmitPersonViewModel(
    private val personRepository: PersonRepository
): ViewModel() {

    private val _state = MutableStateFlow(SubmitPersonState())
    private val _selected = MutableStateFlow<Person?>(null)

    val state = combine(
        _state,
        _selected
    ) { state, selected ->
        state.copy(
            persons = state.persons,
            selectedPerson = state.persons.firstOrNull{ it.id == selected?.id },
            isSelectedPersonDetailsOpen = selected != null
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        SubmitPersonState()
    )

    init {
        getAllPersonsByUserId()
    }

    private fun getAllPersonsByUserId() {
        viewModelScope.launch {
            currentUser?.id?.let { id ->
                personRepository.getAllPersonsByUserId(id).collect {
                    when (it) {
                        // FAILURE
                        is Result.Failed -> _state.emit(
                            state.value.copy(
                                isPersonsFailure = true,
                                personsFailureMessage = it.errorMessage
                            )
                        )
                        // LOADING
                        is Result.Loading -> {
                            _state.emit(
                                state.value.copy(
                                    isPersonsLoading = true
                                )
                            )
                            delay(loadingInMillis)
                        }
                        // SUCCESS
                        is Result.Success -> _state.emit(
                            state.value.copy(
                                isPersonsFailure = false,
                                personSubmitFailureMessage = null,
                                isPersonsLoading = false,
                                persons = it.data
                            )
                        )
                    }
                }
            }
        }
    }

    private val _person = MutableStateFlow(Person())
    val person = _person.asStateFlow()

    private fun submitPerson(person: Person) {
        viewModelScope.launch {
            val personFinalized = person.copy(
                academy = currentUser?.academy,
                userId = currentUser?.id,
                userFirstname = currentUser?.firstname,
                userLastname = currentUser?.lastname
            )
            // Await for result
            personFinalized.let {
                personRepository.submitPerson(personFinalized).collect {
                    when (it) {
                        // FAILURE
                        is Result.Failed -> _state.emit(
                            state.value.copy(
                                isPersonSubmitFailure = true,
                                personSubmitFailureMessage = it.errorMessage
                            )
                        )
                        // LOADING
                        is Result.Loading -> {
                            _state.emit(
                                state.value.copy(
                                    isPersonSubmitLoading = true
                                )
                            )
                            delay(loadingInMillis)
                        }
                        // SUCCESS
                        is Result.Success -> _state.emit(
                            state.value.copy(
                                isPersonSubmitFailure = false,
                                personSubmitFailureMessage = null,
                                isPersonSubmitLoading = false,
                            )
                        )
                    }

                }
            }
        }
    }

    private fun updatePersonValidatedModules(person: Person) {
        viewModelScope.launch {
            personRepository.updatePerson(person).collect {
                when (it) {
                    is Result.Failed -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> Unit
                }
            }
        }
    }


    fun onEvent(event: SubmitPersonEvent) {
        when (event) {

            is SubmitPersonEvent.OnPersonRoleChange -> {
                _person.value =
                    person.value.copy(
                        role = event.personRole
                    )
            }

            is SubmitPersonEvent.OnPersonFirstNameChange -> {
                _person.value =
                    person.value.copy(
                        firstname = event.personFirstName
                    )
            }

            is SubmitPersonEvent.OnPersonLastNameChange -> {
                _person.value =
                    person.value.copy(
                        lastname = event.personLastName
                )
            }

            SubmitPersonEvent.OnPersonsModeButtonClick -> {
                _state.value =
                    state.value.copy(
                        submitPersonMode = false,
                        personsMode = true
                    )
            }

            SubmitPersonEvent.OnSubmitPersonModeButtonClick -> {
                _state.value =
                    state.value.copy(
                        submitPersonMode = true,
                        personsMode = false
                )
            }

            SubmitPersonEvent.DismissPersonDetailsSheet -> {
                _selected.value = null
            }

            is SubmitPersonEvent.OnPersonSelect -> {
                _selected.value = event.person
            }

            SubmitPersonEvent.OnSubmitPersonButtonClick -> {

                val result = SubmitPersonValidator.validatePerson(person.value)

                // Verify if any element is null
                val errors = listOfNotNull(
                    result.personRoleError,
                    result.personFirstnameError,
                    result.personLastnameError
                )

                if (errors.isEmpty()) {

                    submitPerson(person.value) // submit person after validation

                } else {

                    if (!result.personRoleError.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            isRoleFieldError = true
                        )
                    }

                    if (!result.personFirstnameError.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            isFirstnameFieldError = true
                        )
                    }

                    if (!result.personLastnameError.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            isLastnameFieldError = true
                        )
                    }

                }
            }

            is SubmitPersonEvent.OnPersonModulesSwitchToggle ->  updateValidatedModules(event.moduleId, event.isChecked)


        }
    }

    private fun updateValidatedModules(moduleId: Int, isChecked: Boolean){
        val currentValidatedModules = state.value.selectedPerson?.validatedModules
        val modulesList = currentValidatedModules?.split(".")?.toMutableList() ?: mutableListOf()

        if (isChecked) {
            // If the switch is checked, add the moduleId to the list if it's not already present
            if (!modulesList.contains(moduleId.toString())) {
                modulesList.add(moduleId.toString())
            }
        } else {
            // If the switch is unchecked, remove the moduleId from the list if it's present
            modulesList.remove(moduleId.toString())
        }
        // Join the list back into a string
        val updatedValidatedModules = modulesList.joinToString(".")

        // Update Person in database with new values
        state.value.selectedPerson?.copy(validatedModules = updatedValidatedModules)
            ?.let { person -> updatePersonValidatedModules(person) }
    }

}