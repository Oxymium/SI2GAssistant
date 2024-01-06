package com.oxymium.si2gassistant.ui

import com.oxymium.si2gassistant.ui.routes.AppScreen

sealed interface AppEvent {
    data class OnLoginButtonClick(val mail: String, val password: String): AppEvent
    data object OnLogoutButtonClick: AppEvent
    data class OnItemMenuButtonClick(val appScreen: AppScreen): AppEvent
}