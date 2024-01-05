package com.oxymium.si2gassistant.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel: ViewModel() {

    private val _state = MutableStateFlow(NavigationState())
    val state = _state.asStateFlow()

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

            is NavigationEvent.OnItemMenuButtonClick -> {
                _state.value = state.value.copy(
                    currentScreen = navigationEvent.route.name
                )
            }
        }

    }
}