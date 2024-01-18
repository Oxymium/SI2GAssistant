package com.oxymium.si2gassistant.ui.scenes.greetings

sealed interface GreetingsEvent {
    data object OnGreetingsButtonClick: GreetingsEvent
    data object OnTestingButtonClick: GreetingsEvent

    data object OnAcademyClick: GreetingsEvent
    data object OnModuleClick: GreetingsEvent
    data object OnAnnouncementClick: GreetingsEvent
    data class OnBugTicketClick(val quantity: Int): GreetingsEvent
    data class OnPersonQuantityClick(val quantity: Int): GreetingsEvent
    data class OnSuggestionQuantityClick(val quantity: Int): GreetingsEvent
    data class OnMessageQuantityClick(val quantity: Int): GreetingsEvent
}