package com.oxymium.si2gassistant.ui.scenes.splash

sealed interface SplashEvent {
    data object OnClickLoginButton: SplashEvent
    data class OnLoginMailChanged(val mail: String): SplashEvent
    data class OnLoginPasswordChanged(val password: String): SplashEvent
}