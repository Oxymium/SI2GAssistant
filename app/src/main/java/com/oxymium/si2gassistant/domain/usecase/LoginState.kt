package com.oxymium.si2gassistant.domain.usecase

import com.oxymium.si2gassistant.domain.entities.Auth
import com.oxymium.si2gassistant.domain.entities.User
import com.oxymium.si2gassistant.ui.scenes.login.components.Login

data class LoginState(
    val auth: Auth? = null,
    val login: Login? = null,
    val user: User? = null,
    val isMailFieldError: Boolean = false,
    val isPasswordFieldError: Boolean = false,
    val isAuthLoading: Boolean = false,
    val authError: AuthError? = null,
    val isAuthSuccessful: Boolean = false,
    val isUserLoading: Boolean = true,
    val userError: String? = null,
    val isUserSuccessful: Boolean = false,
)

data class AuthError(
    val isError: Boolean,
    val errorMessage: String?
)