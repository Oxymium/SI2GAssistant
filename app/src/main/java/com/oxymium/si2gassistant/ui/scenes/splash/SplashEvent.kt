package com.oxymium.si2gassistant.ui.scenes.splash

sealed interface SplashEvent {
    data object OnLoginButtonClick: SplashEvent
    data class OnLoginMailChange(val mail: String): SplashEvent
    data class OnLoginPasswordChange(val password: String): SplashEvent
    data object OnButtonClickCallback: SplashEvent
}