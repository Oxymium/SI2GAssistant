package com.oxymium.si2gassistant.ui.scenes.greetings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.AnnouncementRepository
import com.oxymium.si2gassistant.domain.states.GreetingsState
import com.oxymium.si2gassistant.loadingInMillis
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GreetingsViewModel(
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

}