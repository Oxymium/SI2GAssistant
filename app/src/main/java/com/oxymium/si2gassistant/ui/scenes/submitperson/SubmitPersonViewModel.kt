package com.oxymium.si2gassistant.ui.scenes.submitperson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.data.repository.GLOBAL_USER
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.loadingInMillis
import com.oxymium.si2gassistant.ui.scenes.submitperson.components.SubmitPersonValidator
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

    init {
        //getPersons()
    }

    private val _state = MutableStateFlow(SubmitPersonState())
    private val _selected = MutableStateFlow<Person?>(null)

    // Reference of validated modules kept to delay the update
    private val _validatedModules = MutableStateFlow<String?>(String())

    val state = combine(
        _state, personRepository.getAllPersonsByUserId(GLOBAL_USER!!.id!!), _selected, _validatedModules
    ) { state, persons, selected, validatedModules ->
        state.copy(
            persons = persons,
            selectedPerson = persons.firstOrNull{ it.id == selected?.id },
            isSelectedPersonDetailsOpen = selected != null,
            selectedPersonValidatedModules = validatedModules
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SubmitPersonState())

    private val _person = MutableStateFlow(Person())
    val person = _person.asStateFlow()
    private fun updatePerson(person: Person) {
        viewModelScope.launch {
            _person.emit(person)
        }
    }
    private fun submitPerson(person: Person) {
        viewModelScope.launch {
            val personFinalized = person.copy(
                academy = GLOBAL_USER?.academy,
                userId = GLOBAL_USER?.id,
                userFirstname = GLOBAL_USER?.firstname,
                userLastname = GLOBAL_USER?.lastname
            )
            // Await for result
            personFinalized.let {
                personRepository.submitPerson(personFinalized).collect {
                    when (it) {
                        is Result.Failed -> _state.emit(
                            state.value.copy(
                                isPersonSubmitFailure = true,
                                personSubmitFailureMessage = it.errorMessage
                            )
                        )

                        is Result.Loading -> {
                            _state.emit(
                                state.value.copy(
                                    isPersonSubmitLoading = true
                                )
                            )
                            delay(loadingInMillis)
                        }

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

    private fun updatePersonModules(person: Person) {
        viewModelScope.launch {
            personRepository.updatePersonModules(person)
        }
    }

    private fun updateValidatedModules(moduleId: Int, isChecked: Boolean) {
        val currentModules = _validatedModules.value?.split(", ")?.map { it.toInt() }?.toMutableList() ?: mutableListOf()
        if (isChecked) currentModules.add(moduleId) else currentModules.remove(moduleId)
        _state.value = state.value.copy(selectedPersonValidatedModules = currentModules.joinToString(", "))
    }

    fun onEvent(submitPersonEvent: SubmitPersonEvent) {
        when (submitPersonEvent) {
            is SubmitPersonEvent.OnPersonRoleChanged -> updatePerson(person.value.copy(role = submitPersonEvent.personRole))
            is SubmitPersonEvent.OnPersonFirstNameChanged -> updatePerson(person.value.copy(firstname = submitPersonEvent.personFirstName))
            is SubmitPersonEvent.OnPersonLastNameChanged -> updatePerson(person.value.copy(lastname = submitPersonEvent.personLastName))

            SubmitPersonEvent.OnPersonsModeButtonClicked -> {
                val newState = state.value.copy(
                    submitPersonMode = false,
                    personsMode = true
                )
                _state.value = newState
            }
            SubmitPersonEvent.OnSubmitPersonModeButtonClicked -> {
                val newState = state.value.copy(
                    submitPersonMode = true,
                    personsMode = false
                )
                _state.value = newState
            }

            SubmitPersonEvent.DismissPersonDetailsSheet -> {
                _selected.value = null
                _validatedModules.value = null
            }

            is SubmitPersonEvent.OnSelectedPerson -> {
                _selected.value = submitPersonEvent.person
                _validatedModules.value = submitPersonEvent.person.validatedModules // get a reference of validatedModules because we don't want delayed updates
            }

            SubmitPersonEvent.OnSubmitPersonButtonClicked -> {
                // Initial reset
                _state.value = state.value.copy(
                    isRoleFieldError = false,
                    isFirstnameFieldError = false,
                    isLastnameFieldError = false
                )

                val result = SubmitPersonValidator.validatePerson(person.value)
                // Verify if any element is null
                val errors = listOfNotNull(
                    result.personRoleError,
                    result.personFirstnameError,
                    result.personLastnameError
                )

                if (errors.isEmpty()) {
                    _state.value = state.value.copy(
                        isRoleFieldError = false,
                        isFirstnameFieldError = false,
                        isLastnameFieldError = false,
                    )

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

            is SubmitPersonEvent.OnPersonModulesSwitchToggle -> updateValidatedModules(submitPersonEvent.moduleId, submitPersonEvent.isChecked)

            is SubmitPersonEvent.OnPersonModulesUpdateButtonClicked -> {
                val newPerson = state.value.selectedPerson?.copy(
                    validatedModules = submitPersonEvent.modules
                )
                newPerson?.let { updatePersonModules(newPerson) }
            }

        }
    }
}