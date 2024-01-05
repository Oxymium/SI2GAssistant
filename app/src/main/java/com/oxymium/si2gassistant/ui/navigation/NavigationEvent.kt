package com.oxymium.si2gassistant.ui.navigation

import com.oxymium.si2gassistant.domain.entities.User
import com.oxymium.si2gassistant.ui.routes.AppScreens

sealed interface NavigationEvent {

    data class OnLoginButtonClick(val user: User? = null): NavigationEvent
    data object OnLogoutButtonClick: NavigationEvent
    data class OnItemMenuButtonClick(val route: AppScreens): NavigationEvent

}