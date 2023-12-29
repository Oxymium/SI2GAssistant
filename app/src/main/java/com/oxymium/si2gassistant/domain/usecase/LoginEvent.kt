package com.oxymium.si2gassistant.domain.usecase

sealed interface LoginEvent {
    data object OnClickLoginButton: LoginEvent
    data class OnLoginMailChanged(val mail: String): LoginEvent
    data class OnLoginPasswordChanged(val password: String): LoginEvent
}