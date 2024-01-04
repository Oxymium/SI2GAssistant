package com.oxymium.si2gassistant.ui.scenes

import com.oxymium.si2gassistant.domain.entities.User

sealed interface NavigationEvent {

    data class OnLoginButtonClick(val user: User? = null): NavigationEvent

    data object OnLogoutButtonClick: NavigationEvent
    data object OnMetricsButtonClick: NavigationEvent
    data object OnBugTicketsButtonClick: NavigationEvent
    data object OnGreetingsButtonClick: NavigationEvent
    data object OnPersonsButtonClick: NavigationEvent
    data object OnReportBugButtonClick: NavigationEvent
    data object OnSubmitPersonButtonClick: NavigationEvent

    data object OnSubmitSuggestionButtonClick: NavigationEvent
    data object OnSuggestionsButtonClick: NavigationEvent

}