package com.oxymium.si2gassistant.ui.scenes.submitperson.components

import com.oxymium.si2gassistant.domain.entities.Person

object SubmitPersonValidator {

    fun validatePerson(person: Person): PersonValidationResult {

        var result = PersonValidationResult()

        if (person.role.isNullOrEmpty() || person.role.isBlank()) {
            result = result.copy(
                personRoleError = "Error: role cannot be empty")
        }

        if (person.firstname.isNullOrEmpty() || person.firstname.isBlank()) {
            result = result.copy(
                personFirstnameError = "Error: firstname cannot be empty")
        }

        if (person.lastname.isNullOrEmpty() || person.lastname.isBlank()) {
            result = result.copy(
                personLastnameError = "Error: lastname cannot be empty")
        }

        return result
    }
}

data class PersonValidationResult(
    val personRoleError: String? = null,
    val personFirstnameError: String? = null,
    val personLastnameError: String? = null
)