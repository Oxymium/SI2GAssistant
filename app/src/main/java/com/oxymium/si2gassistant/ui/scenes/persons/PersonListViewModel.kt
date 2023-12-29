package com.oxymium.si2gassistant.ui.scenes.persons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomPerson
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonFilter
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonListEvent
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonListState
import com.oxymium.si2gassistant.ui.scenes.suggestions.components.SuggestionFilter
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
        _state, personRepository.getAllPersons(), _filter, _selected)
    { state, persons, filter, selected ->
        state.copy(
            personList = when (filter) {
                PersonFilter.DefaultFilter -> persons
                is PersonFilter.Search -> persons.filter {
                    it.academy?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.firstname?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.lastname?.contains(filter.search ?: "", ignoreCase = true) == true
                }
            },
            selectedPerson = selected
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), PersonListState())

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