package com.oxymium.si2gassistant.ui.scenes.greetings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class GreetingsViewModel: ViewModel() {

    val state = MutableStateFlow(GreetingsState())

    fun onEvent(event: GreetingsEvent) {
        when (event) {
            GreetingsEvent.OnLogoutButtonClicked -> { println("LOGOUT CLICKED") }
        }
    }
}