package com.oxymium.si2gassistant.ui.scenes.greetings

sealed interface GreetingsEvent {
    data object OnLogoutButtonClicked: GreetingsEvent

}