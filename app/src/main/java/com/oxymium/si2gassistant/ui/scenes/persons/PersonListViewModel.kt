package com.oxymium.si2gassistant.ui.scenes.persons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomPerson
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.loadingInMillis
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonFilter
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonListEvent
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonListState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonListViewModel(private val personRepository: PersonRepository): ViewModel() {

    private val _state = MutableStateFlow(PersonListState())
    private val _selected = MutableStateFlow<Person?>(null)
    private val _filter = MutableStateFlow<PersonFilter>(PersonFilter.DefaultFilter)

    val state = combine(
        _state, _filter, _selected)
    { state, filter, selected ->
        state.copy(
            persons = when (filter) {
                PersonFilter.DefaultFilter -> state.persons
                is PersonFilter.Search -> state.persons.filter {
                    it.academy?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.firstname?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.lastname?.contains(filter.search ?: "", ignoreCase = true) == true
                }
            },
            selectedPerson = selected
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), PersonListState())

    init {
        getAllPersons()
    }

    private fun getAllPersons() {
        viewModelScope.launch {
            personRepository.getAllPersons().collect {
                when (it) {
                    // FAILURE
                    is Result.Failed ->
                        _state.value = state.value.copy(
                            isPersonsFailure = true,
                            personsFailureMessage = it.errorMessage
                        )

                    // LOADING
                    is Result.Loading -> {
                        _state.value = state.value.copy(
                            isPersonsLoading = true
                        )
                        delay(loadingInMillis)
                    }

                    // SUCCESS
                    is Result.Success ->
                        _state.value = state.value.copy(
                            isPersonsFailure = false,
                            isPersonsLoading = false,
                            persons = it.data
                        )

                }
            }
        }
    }

    private fun updatePersonFilter(personFilter: PersonFilter) {
        viewModelScope.launch {
            _filter.emit(personFilter)
        }
    }

    private fun pushRandomPerson(person: Person) {
        viewModelScope.launch {
            personRepository.submitPerson(person)
        }
    }

    fun onEvent(event: PersonListEvent) {
        when (event) {
            is PersonListEvent.OnPersonClick -> {
                _selected.value = event.person
            }
            is PersonListEvent.OnSearchTextInput -> updatePersonFilter(PersonFilter.Search(event.search))
            PersonListEvent.OnRandomPersonButtonClicked -> pushRandomPerson(provideRandomPerson())
        }
    }
}