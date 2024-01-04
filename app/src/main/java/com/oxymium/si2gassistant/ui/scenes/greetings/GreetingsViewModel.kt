package com.oxymium.si2gassistant.ui.scenes.greetings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomPerson
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomSuggestion
import com.oxymium.si2gassistant.domain.repository.AnnouncementRepository
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import com.oxymium.si2gassistant.loadingInMillis
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GreetingsViewModel(
    private val bugTicketRepository: BugTicketRepository,
    private val suggestionRepository: SuggestionRepository,
    private val personRepository: PersonRepository,
    private val announcementRepository: AnnouncementRepository
): ViewModel() {

    private val _state = MutableStateFlow(GreetingsState())
    val state = _state.asStateFlow()

    init {
        getAllAnnouncements()
    }

    private fun getAllAnnouncements() {
        viewModelScope.launch {
            announcementRepository.getAllAnnouncements().collect {
                when (it) {
                    // FAILURE
                    is Result.Failed ->
                        _state.emit(
                            state.value.copy(
                                isAnnouncementsFailure = true,
                                announcementsFailureMessage = it.errorMessage
                            )
                        )

                    // LOADING
                    is Result.Loading -> {
                        _state.emit(
                            state.value.copy(
                                isAnnouncementsLoading = true
                            )
                        )
                        delay(loadingInMillis)
                    }

                    // SUCCESS
                    is Result.Success -> {
                        _state.emit(
                            state.value.copy(
                                isAnnouncementsFailure = false,
                                announcementsFailureMessage = null,
                                isAnnouncementsLoading = false,
                                announcements = it.data
                            )
                        )
                    }

                }
            }
        }
    }

    private fun pushRandomBugTicket() {
        viewModelScope.launch {
            bugTicketRepository.submitBugTicket( provideRandomBugTicket() ).collect {
                when (it) {
                    is Result.Failed -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> Unit
                }
            }
        }
    }

    private fun pushRandomSuggestion() {
        viewModelScope.launch {
            suggestionRepository.submitSuggestion( provideRandomSuggestion() ).collect {
                when (it) {
                    is Result.Failed -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> Unit
                }
            }
        }
    }

    private fun pushRandomPerson() {
        viewModelScope.launch {
            personRepository.submitPerson( provideRandomPerson() ).collect {
                when (it) {
                    is Result.Failed -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> Unit
                }
            }
        }
    }

    fun onEvent(event: GreetingsEvent) {
        when (event) {
            GreetingsEvent.OnRandomBugTicketButtonClicked -> pushRandomBugTicket() // testing purposes
            GreetingsEvent.OnRandomSuggestionButtonClicked -> pushRandomSuggestion() // testing purposes
            GreetingsEvent.OnRandomPersonButtonClicked -> pushRandomPerson() // testing purposes
        }
    }
}