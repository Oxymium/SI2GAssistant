package com.oxymium.si2gassistant.ui.scenes.metrics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.Auth
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

    init {
        getAllBugTickets()
        getAllAcademies()
        getAllModules()
        getAllUsers()
        getAllPersons()
        getAllSuggestions()
    }

    private val _state = MutableStateFlow(MetricsState())
    val state = _state.asStateFlow()

    private fun getAllBugTickets() {
        viewModelScope.launch {
            bugTicketRepository.getAllBugTickets().collect { bugTicketList ->
                val newState = state.value.copy(
                    bugTicketList = bugTicketList
                )
                _state.emit(newState)
            }
        }
    }

    private fun getAllAcademies() {
        viewModelScope.launch {
            academyRepository.getAllAcademies().collect { academies ->
                val newState = state.value.copy(
                    academies = academies
                )
                _state.emit(newState)
            }
        }
    }

    private fun getAllModules() {
        viewModelScope.launch {
            moduleRepository.getAllModules().collect { modules ->
                val newState = state.value.copy(
                    modules = modules
                )
                _state.emit(newState)
            }
        }
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            userRepository.getAllUsers().collect { users ->
                val newState = state.value.copy(
                    users = users
                )
                _state.emit(newState)
            }
        }
    }
    private fun getAllPersons() {
        viewModelScope.launch {
            personRepository.getAllPersons().collect { persons ->
                val newState = state.value.copy(
                    persons = persons
                )
                _state.emit(newState)
            }
        }
    }

    private fun getAllSuggestions() {
        viewModelScope.launch {
            suggestionRepository.getAllSuggestions().collect { suggestions ->
                val newState = state.value.copy(
                    suggestions = suggestions
                )
                _state.emit(newState)
            }
        }
    }

}