package com.oxymium.si2gassistant.ui

import com.oxymium.si2gassistant.domain.entities.User
import com.oxymium.si2gassistant.ui.routes.AppScreens

data class NavigationState(
    val currentUser: User? = null,
    val currentScreen: String? = AppScreens.GREETINGS_SCREEN.name // default value initial screen
)