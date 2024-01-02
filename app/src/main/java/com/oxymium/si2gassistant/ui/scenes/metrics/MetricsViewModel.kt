package com.oxymium.si2gassistant.ui.scenes.metrics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.AcademyRepository
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.domain.repository.ModuleRepository
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import com.oxymium.si2gassistant.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MetricsViewModel(
    private val bugTicketRepository: BugTicketRepository,
    private val academyRepository: AcademyRepository,
    private val moduleRepository: ModuleRepository,
    private val userRepository: UserRepository,
    private val personRepository: PersonRepository,
    private val suggestionRepository: SuggestionRepository
): ViewModel() {

    private val _state = MutableStateFlow(MetricsState())
    val state = _state.asStateFlow()

    init {
        getAllBugTickets()
        getAllAcademies()
        getAllModules()
        getAllUsers()
        getAllPersons()
        getAllSuggestions()
    }

    private fun getAllBugTickets() {
        viewModelScope.launch {
            bugTicketRepository.getAllBugTickets().collect {
                when (it) {
                    is Result.Failed -> println("failed")
                    is Result.Loading -> println("loading")
                    is Result.Success -> _state.emit(
                        state.value.copy(
                            bugTickets = it.data
                        )
                    )
                }
            }
        }
    }

    private fun getAllAcademies() {
        viewModelScope.launch {
            academyRepository.getAllAcademies().collect {
                when (it) {
                    is Result.Failed -> println("failed")
                    is Result.Loading -> println("loading")
                    is Result.Success -> _state.emit(
                        state.value.copy(
                            academies = it.data
                        )
                    )
                }
            }
        }
    }

    private fun getAllModules() {
        viewModelScope.launch {
            moduleRepository.getAllModules().collect {
                when (it) {
                    is Result.Failed -> println("Failed")
                    is Result.Loading -> println("Loading")
                    is Result.Success -> _state.emit(
                        state.value.copy(
                            modules = it.data
                        )
                    )
                }
            }
        }
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            userRepository.getAllUsers().collect {
                when (it) {
                    is Result.Failed -> println("Failed")
                    is Result.Loading -> println("Loading")
                    is Result.Success -> _state.emit(
                        state.value.copy(
                            users = it.data
                        )
                    )
                }

            }
        }
    }

    private fun getAllPersons() {
        viewModelScope.launch {
            personRepository.getAllPersons().collect {
                when (it) {
                    is Result.Failed -> println("Suggestions: failed")
                    is Result.Loading -> println("Suggestion: loading")
                    is Result.Success -> _state.emit(
                        state.value.copy(
                            persons = it.data
                        ))
                }

            }
        }
    }

    private fun getAllSuggestions() {
        viewModelScope.launch {
            suggestionRepository.getAllSuggestions().collect {
                when (it) {
                    is Result.Failed -> println("Suggestions: failed")
                    is Result.Loading -> println("Suggestion: loading")
                    is Result.Success -> _state.emit(
                        state.value.copy(
                            suggestions = it.data
                        ))
                }
            }
        }
    }

    fun onEvent(metricsScreenEvent: MetricsScreenEvent) {
        when (metricsScreenEvent) {
            MetricsScreenEvent.OnBugTicketsButtonClick -> {
                val newState = state.value.copy(
                    isBugTicketMetricsScreen = true,
                    isOverallMetricsScreen = false
                )
                _state.value = newState
            }
            MetricsScreenEvent.OnOverallMetricsButtonClick -> {
                val newState = state.value.copy(
                    isBugTicketMetricsScreen = false,
                    isOverallMetricsScreen = true
                )
                _state.value = newState
            }
        }

    }

}