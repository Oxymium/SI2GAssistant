package com.oxymium.si2gassistant.domain.usecase

import com.oxymium.si2gassistant.domain.entities.Auth

data class LoginState(
    val auth: Auth? = null,
    val isLoading: Boolean = false,
    val loginError: LoginError? = null,
    val isSuccessful: Boolean = false
)

data class LoginError(
    val isError: Boolean,
    val errorMessage: String?
)