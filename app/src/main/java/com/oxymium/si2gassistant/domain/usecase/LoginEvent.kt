package com.oxymium.si2gassistant.domain.usecase

sealed interface LoginEvent {
    data object OnClickLoginButton: LoginEvent
}