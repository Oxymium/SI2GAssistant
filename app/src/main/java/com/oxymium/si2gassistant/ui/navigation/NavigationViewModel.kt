package com.oxymium.si2gassistant.ui.navigation

import androidx.lifecycle.ViewModel
import com.oxymium.si2gassistant.domain.states.NavigationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel: ViewModel() {

    private val _state = MutableStateFlow(NavigationState())
    val state = _state.asStateFlow()

    fun onEvent(event: NavigationEvent) {
        when (event) {

            is NavigationEvent.OnLoginButtonClick -> {
                _state.value =
                    state.value.copy(
                        currentUser = event.user
                    )
            }

            NavigationEvent.OnLogoutButtonClick -> {
                _state.value =
                    state.value.copy(
                        currentUser = null
                    )
            }

            is NavigationEvent.OnItemMenuButtonClick -> {
                _state.value =
                    state.value.copy(
                        currentScreen = event.route.name
                )
            }

        }
    }
}