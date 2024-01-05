package com.oxymium.si2gassistant.domain.states

import com.oxymium.si2gassistant.domain.entities.Person

data class PersonListState(
    val persons: List<Person> = emptyList(),
    val selectedPerson: Person? = null,
    val isBottomSheetOpen: Boolean = false,
    val isPersonsLoading: Boolean = false,
    val isPersonsFailure: Boolean = false,
    val personsFailureMessage: String? = null
)