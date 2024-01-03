package com.oxymium.si2gassistant.ui.scenes.submitperson

import com.oxymium.si2gassistant.domain.entities.Person

data class SubmitPersonState(
    val persons: List<Person> = emptyList(),
    val selectedPerson: Person? = null,
    val selectedPersonValidatedModules: String? = null,
    val newPerson: Person? = null,
    val submitPersonMode: Boolean = false,
    val personsMode: Boolean = true, // default screen to load
    val isSelectedPersonDetailsOpen: Boolean = false,
    // Persons state ---------------------
    val isPersonsLoading: Boolean = false,
    val isPersonsFailure: Boolean = false,
    val personsFailureMessage: String? = null,
    // Submit fields -------------------------
    val isRoleFieldError: Boolean = false,
    val isFirstnameFieldError: Boolean = false,
    val isLastnameFieldError: Boolean = false,
    // Submit Person state ----------------------
    val isPersonSubmitLoading: Boolean = false,
    val isPersonSubmitFailure: Boolean = false,
    val personSubmitFailureMessage: String? = null
    // -------------------------------------------
)