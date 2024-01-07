package com.oxymium.si2gassistant.domain.validators

object LoginValidator {

    fun validateLogin(login: Login): LoginValidationData {
        return LoginValidationData(
            loginMailError = login.mail.isEmpty(),
            loginPasswordError = login.password.isEmpty()
        )
    }
}

data class LoginValidationData(
    val loginMailError: Boolean,
    val loginPasswordError: Boolean
) {
    fun hasErrors(): Boolean {
        return loginMailError || loginPasswordError
    }
}

data class Login(
    val mail: String,
    val password: String
)