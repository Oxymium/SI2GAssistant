package com.oxymium.si2gassistant.domain

import com.oxymium.si2gassistant.domain.entities.Auth

object AuthValidator {

    fun validateAuth(auth: Auth): ValidationResult {
        var result = ValidationResult()

        if (auth.mail.isBlank()) {
            result = result.copy(mailError = "Missing mail")
        }

        if (auth.mail.isEmpty()) {
            result = result.copy(passwordError = "Missing password")
        }

        return result
    }
}

data class ValidationResult(
    val mailError: String? = null,
    val passwordError: String? = null
)