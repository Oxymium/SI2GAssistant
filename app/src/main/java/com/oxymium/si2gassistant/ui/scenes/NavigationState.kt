package com.oxymium.si2gassistant.ui.scenes

import com.oxymium.si2gassistant.domain.entities.User

data class NavigationState(
    val currentUser: User? = null,
    val navigationRoute: String? = null,
    val navigationScreen: String? = null
)