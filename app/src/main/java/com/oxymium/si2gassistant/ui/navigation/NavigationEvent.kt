package com.oxymium.si2gassistant.ui.navigation

sealed interface NavigationEvent {
    data object OnLogoutButtonClick: NavigationEvent
}