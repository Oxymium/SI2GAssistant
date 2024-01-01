package com.oxymium.si2gassistant.ui.scenes.submitperson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.data.repository.GLOBAL_USER
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.ui.scenes.submitperson.components.SubmitPersonValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SubmitPersonViewModel(
    private val personRepository: PersonRepository
): ViewModel() {

    init {
        getPersons()
    }

    private val _state = MutableStateFlow(SubmitPersonState())
    val state = _state.asStateFlow()

    private val _person = MutableStateFlow(Person())
    val person = _person.asStateFlow()

    private fun submitPerson(person: Person) {
        viewModelScope.launch {
            val personFinalized = person.copy(
                academy = GLOBAL_USER?.academy,
                userId = GLOBAL_USER?.id,
                userFirstname = GLOBAL_USER?.firstname,
                userLastname = GLOBAL_USER?.lastname
            )
            personFinalized.let { personRepository.submitPerson(personFinalized) }
            _person.value = Person() // reset after push
        }
    }

    private fun getPersons() {
        viewModelScope.launch {
            personRepository.getAllPersonsByUserId(GLOBAL_USER!!.id!!).collect {
                val newState = state.value.copy(persons = it)
                _state.value = newState
            }
        }
    }

    private fun updatePersonModules(person: Person) {
        viewModelScope.launch {
            personRepository.updatePersonModules(person)
        }
    }

    fun onEvent(submitPersonEvent: SubmitPersonEvent) {
        when (submitPersonEvent) {
            is SubmitPersonEvent.OnPersonRoleChanged -> {
                val person = person.value.copy(
                    role = submitPersonEvent.personRole
                )
                _person.value = person
            }

            is SubmitPersonEvent.OnPersonFirstNameChanged -> {
                val person = person.value.copy(
                    firstname = submitPersonEvent.personFirstName
                )
                _person.value = person
            }

            is SubmitPersonEvent.OnPersonLastNameChanged -> {
                val person = person.value.copy(
                    lastname = submitPersonEvent.personLastName
                )
                _person.value = person
            }

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
                val newState = state.value.copy(
                    isSelectedPersonDetailsOpen = false
                )
                _state.value = newState
            }

            is SubmitPersonEvent.OnSelectedPerson -> {
                val newState = state.value.copy(
                    isSelectedPersonDetailsOpen = true,
                    selectedPerson = submitPersonEvent.person
                )
                _state.value = newState
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

            is SubmitPersonEvent.OnPersonModulesUpdateButtonClicked -> {
                val newPerson = state.value.selectedPerson?.copy(
                    validatedModules = submitPersonEvent.modules
                )
                newPerson?.let { updatePersonModules(newPerson) }
            }

        }
    }
}