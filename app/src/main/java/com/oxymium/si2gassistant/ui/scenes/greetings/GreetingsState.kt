package com.oxymium.si2gassistant.ui.scenes.greetings

import com.oxymium.si2gassistant.domain.entities.User

data class GreetingsState(
    val currentUser: User? = null
)