package com.oxymium.si2gassistant.ui.scenes.persons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.loadingInMillis
import com.oxymium.si2gassistant.domain.filters.PersonFilter
import com.oxymium.si2gassistant.domain.states.PersonListState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonsViewModel(
    private val personRepository: PersonRepository
): ViewModel() {

    private val _state = MutableStateFlow(PersonListState())
    private val _filter = MutableStateFlow<PersonFilter>(PersonFilter.DefaultFilter)

    val state = combine(
        _state,
        _filter
    )
    { state, filter ->
        state.copy(
            persons = when (filter) {
                PersonFilter.DefaultFilter -> state.persons
                is PersonFilter.Search -> state.persons.filter {
                    it.academy?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.firstname?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.lastname?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.role?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.userFirstname?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.userLastname?.contains(filter.search ?: "", ignoreCase = true) == true
                }
            }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        PersonListState()
    )

    init {
        getAllPersons()
    }

    private fun getAllPersons() {
        viewModelScope.launch {
            personRepository.getAllPersons().collect {
                when (it) {

                    // FAILURE
                    is Result.Failed ->
                        _state.emit(
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
                    is Result.Success ->
                        _state.emit(
                            state.value.copy(
                                isPersonsFailure = false,
                                isPersonsLoading = false,
                                persons = it.data
                            )
                        )

                }
            }
        }
    }

    fun onEvent(event: PersonsEvent) {
        when (event) {
            is PersonsEvent.OnSearchTextChange -> {
                _filter.value = PersonFilter.Search(event.search)
            }

        }
    }
}