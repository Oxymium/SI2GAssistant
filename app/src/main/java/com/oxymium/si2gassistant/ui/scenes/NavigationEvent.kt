package com.oxymium.si2gassistant.ui.scenes

sealed interface NavigationEvent {

    data object OnSplashButtonClicked: NavigationEvent
    data object OnLoginButtonClicked: NavigationEvent

    data object OnBugTicketsButtonClicked: NavigationEvent
    data object OnGreetingsButtonClicked: NavigationEvent

}