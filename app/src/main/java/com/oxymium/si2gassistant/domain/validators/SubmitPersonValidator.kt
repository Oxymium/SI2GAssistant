package com.oxymium.si2gassistant.domain.validators

import com.oxymium.si2gassistant.domain.entities.Person

object SubmitPersonValidator {
    fun validatePerson(person: Person): PersonValidationData {
        return PersonValidationData(
            personRoleError = person.role.isNullOrEmpty(),
            personFirstnameError = person.firstname.isNullOrEmpty(),
            personLastnameError = person.lastname.isNullOrEmpty()
        )
    }

}

data class PersonValidationData(
    val personRoleError: Boolean,
    val personFirstnameError: Boolean,
    val personLastnameError: Boolean
) {
    fun hasErrors(): Boolean {
        return personRoleError || personFirstnameError || personLastnameError
    }
}