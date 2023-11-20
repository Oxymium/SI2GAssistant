package com.oxymium.si2gassistant.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.ui.scenes.AppRoutes
import com.oxymium.si2gassistant.ui.scenes.AppScreens
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.scenes.NavigationState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class NavigationViewModel: ViewModel() {

    private val _state = MutableSharedFlow<NavigationState>(0)
    val state: SharedFlow<NavigationState> get() = _state

    fun updateState(value: NavigationState){
        viewModelScope.launch {
            _state.emit(value)
        }
    }

    fun onEvent(navigationEvent: NavigationEvent) {
        when (navigationEvent) {

            NavigationEvent.OnSplashButtonClicked -> updateState(
                NavigationState(AppScreens.LOGIN_SCREEN.name)
            )
            NavigationEvent.OnLoginButtonClicked -> updateState(
                NavigationState(AppRoutes.SUPER_USER_ROUTE.name)
            )

            NavigationEvent.OnBugTicketsButtonClicked -> updateState(
                NavigationState(AppScreens.GREETINGS_SCREEN.name)
            )
            NavigationEvent.OnGreetingsButtonClicked -> updateState(
                NavigationState(AppScreens.BUG_TICKETS_SCREEN.name)
            )
        }

    }
}