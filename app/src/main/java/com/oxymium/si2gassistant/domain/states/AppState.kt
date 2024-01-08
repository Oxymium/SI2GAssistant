package com.oxymium.si2gassistant.domain.states

import com.oxymium.si2gassistant.domain.entities.User
import com.oxymium.si2gassistant.ui.routes.AppScreen

data class AppState(
    // USER FOR APP
    val currentUser: User? = null,
    // CURRENT SCREEN
    val currentScreen: AppScreen? = AppScreen.GREETINGS_SCREEN, // default value initial screen
    // AUTH QUERY
    val isAuthFailure: Boolean = false,
    val authFailureMessage: String? = null,
    val isAuthLoading: Boolean = false,
    // USER QUERY
    val isUserFailure: Boolean = false,
    val userFailureMessage: String? = null,
    val isUserLoading: Boolean = false
)