package com.oxymium.si2gassistant.ui.scenes.greetings

sealed interface GreetingsEvent {
    data object OnLogoutButtonClicked: GreetingsEvent
    data object OnRandomBugTicketButtonClicked: GreetingsEvent
    data object OnRandomSuggestionButtonClicked: GreetingsEvent
    data object OnRandomPersonButtonClicked: GreetingsEvent

}