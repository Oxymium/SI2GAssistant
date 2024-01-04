package com.oxymium.si2gassistant.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.ui.scenes.AppScreens
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.scenes.NavigationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NavigationViewModel: ViewModel() {

    private val _state = MutableStateFlow(NavigationState())
    private val _route = MutableStateFlow<String?>(null)
    private val _screen = MutableStateFlow<String?>(null)

    val state = combine(
        _state, _route, _screen
    ) { state, route, screen ->

        state.copy(
            navigationRoute = route,
            navigationScreen = screen
        )

    }.stateIn(viewModelScope, SharingStarted.Lazily, NavigationState())

    private fun updateScreen(appScreens: AppScreens) {
        viewModelScope.launch {
            _screen.emit(appScreens.name)
        }
    }


    fun onEvent(navigationEvent: NavigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.OnLoginButtonClick ->
                _state.value = state.value.copy(
                    currentUser = navigationEvent.user
            )

            NavigationEvent.OnLogoutButtonClick -> {
                _state.value = state.value.copy(
                    currentUser = null
                )
            }

            NavigationEvent.OnGreetingsButtonClick -> updateScreen(AppScreens.GREETINGS_SCREEN)
            NavigationEvent.OnMetricsButtonClick -> updateScreen(AppScreens.METRICS_SCREEN)
            NavigationEvent.OnPersonsButtonClick -> updateScreen(AppScreens.PERSONS_SCREEN)
            NavigationEvent.OnBugTicketsButtonClick -> updateScreen(AppScreens.BUG_TICKETS_SCREEN)
            NavigationEvent.OnReportBugButtonClick -> updateScreen(AppScreens.REPORT_BUG_SCREEN)
            NavigationEvent.OnSubmitPersonButtonClick -> updateScreen(AppScreens.SUBMIT_PERSON_SCREEN)
            NavigationEvent.OnSubmitSuggestionButtonClick -> updateScreen(AppScreens.SUBMIT_SUGGESTION_SCREEN)
            NavigationEvent.OnSuggestionsButtonClick -> updateScreen(AppScreens.SUGGESTIONS_SCREEN)

        }

    }
}