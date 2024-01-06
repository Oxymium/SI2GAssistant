package com.oxymium.si2gassistant.domain.states

data class SplashState(
    val isMailFieldError: Boolean = false,
    val isPasswordFieldError: Boolean = false,
    val isLogoScreen: Boolean = true, // default
    val isLoginScreen: Boolean = false,
    val authQuery: AuthQuery? = null
)

data class AuthQuery(
    val isReady: Boolean = false,
    val mail: String? = null,
    val password: String? = null
)