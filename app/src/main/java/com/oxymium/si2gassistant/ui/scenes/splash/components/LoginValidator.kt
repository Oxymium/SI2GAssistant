package com.oxymium.si2gassistant.ui.scenes.splash.components

object LoginValidator {

    fun validateLogin(login: Login): LoginValidationResult {
        var result = LoginValidationResult()

        if (login.email.isEmpty() || login.email.isBlank()) {
            result = result.copy(loginMailError = "Error: mail cannot be empty.")
        }

        if (login.password.isEmpty() || login.password.isBlank()) {
            result = result.copy(loginPasswordError = "Error: password cannot be empty.")
        }

        return result
    }
}

data class LoginValidationResult(
    val loginMailError: String? = null,
    val loginPasswordError: String? = null
)

data class Login(
    val email: String,
    val password: String
)