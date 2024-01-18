package com.oxymium.si2gassistant.ui.scenes.greetings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.entities.Announcement
import com.oxymium.si2gassistant.domain.entities.Module
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.mock.ALL_ACADEMIES
import com.oxymium.si2gassistant.domain.mock.ALL_ANNOUNCEMENTS
import com.oxymium.si2gassistant.domain.mock.ALL_MODULES
import com.oxymium.si2gassistant.domain.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.mock.provideRandomMessage
import com.oxymium.si2gassistant.domain.mock.provideRandomPerson
import com.oxymium.si2gassistant.domain.mock.provideRandomSuggestion
import com.oxymium.si2gassistant.domain.repository.AnnouncementRepository
import com.oxymium.si2gassistant.domain.repository.TestRepository
import com.oxymium.si2gassistant.domain.states.GreetingsState
import com.oxymium.si2gassistant.loadingInMillis
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GreetingsViewModel(
    private val announcementRepository: AnnouncementRepository,
    private val testRepository: TestRepository
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

    private fun submitAcademy(
        academy: Academy
    ) {
        viewModelScope.launch {
            testRepository.submitAcademy(academy)
        }
    }

    private fun submitModule(
        module: Module
    ) {
        viewModelScope.launch {
            testRepository.submitModules(module)
        }
    }

    private fun submitBugTicket(
        quantity: Int
    ) {
        viewModelScope.launch {
            repeat(quantity) {
                testRepository.submitBugTicket( provideRandomBugTicket() )
            }
        }
    }

    private fun submitMessage(
        quantity: Int
    ) {
        viewModelScope.launch {
            repeat(quantity) {
                testRepository.submitMessage( provideRandomMessage() )
            }
        }
    }

    private fun submitPerson(
        quantity: Int
    ) {
        viewModelScope.launch {
            repeat(quantity) {
                testRepository.submitPerson( provideRandomPerson() )
            }
        }
    }

    private fun submitSuggestion(
        quantity: Int
    ) {
        viewModelScope.launch {
            repeat(quantity) {
                testRepository.submitSuggestion( provideRandomSuggestion() )
            }
        }
    }

    private fun submitAnnouncement(
        announcement: Announcement
    ) {
        viewModelScope.launch {
            testRepository.submitAnnouncement(announcement)
        }
    }



    fun onEvent(event: GreetingsEvent) {
        when (event) {

            GreetingsEvent.OnGreetingsButtonClick -> {
                _state.value =
                    state.value.copy(
                        isGreetingsMode = true,
                        isTestingMode = false
                    )
            }

            GreetingsEvent.OnTestingButtonClick -> {
                _state.value =
                    state.value.copy(
                        isGreetingsMode = false,
                        isTestingMode = true
                    )
            }

            is GreetingsEvent.OnAcademyClick -> {
                ALL_ACADEMIES.forEach {
                    submitAcademy(it)
                }
            }

            is GreetingsEvent.OnBugTicketClick -> {
                submitBugTicket(event.quantity)
            }

            is GreetingsEvent.OnMessageQuantityClick -> {
                submitMessage(event.quantity)
            }

            is GreetingsEvent.OnPersonQuantityClick -> {
                submitPerson(event.quantity)
            }

            is GreetingsEvent.OnSuggestionQuantityClick -> {
                submitSuggestion(event.quantity)
            }

            GreetingsEvent.OnAnnouncementClick -> {
                ALL_ANNOUNCEMENTS.forEach {
                    submitAnnouncement(it)
                }
            }

            GreetingsEvent.OnModuleClick -> {
                ALL_MODULES.forEach {
                    submitModule(it)
                }
            }
        }
    }

}