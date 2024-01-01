package com.oxymium.si2gassistant.ui.scenes.greetings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomPerson
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomSuggestion
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GreetingsViewModel(
    private val bugTicketRepository: BugTicketRepository,
    private val suggestionRepository: SuggestionRepository,
    private val personRepository: PersonRepository
): ViewModel() {

    val state = MutableStateFlow(GreetingsState())

    private fun pushRandomBugTicket() {
        viewModelScope.launch {
            bugTicketRepository.createBugTicket( provideRandomBugTicket() )
        }
    }

    private fun pushRandomSuggestion() {
        viewModelScope.launch {
            suggestionRepository.submitSuggestion( provideRandomSuggestion() )
        }
    }

    private fun pushRandomPerson() {
        viewModelScope.launch {
            personRepository.submitPerson( provideRandomPerson() )
        }
    }

    fun onEvent(event: GreetingsEvent) {
        when (event) {
            GreetingsEvent.OnLogoutButtonClicked -> { println("LOGOUT CLICKED") }
            GreetingsEvent.OnRandomBugTicketButtonClicked -> pushRandomBugTicket() // testing purposes
            GreetingsEvent.OnRandomSuggestionButtonClicked -> pushRandomSuggestion() // testing purposes
            GreetingsEvent.OnRandomPersonButtonClicked -> pushRandomPerson()
        }
    }
}