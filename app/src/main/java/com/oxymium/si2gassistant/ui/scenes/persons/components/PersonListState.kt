package com.oxymium.si2gassistant.ui.scenes.persons.components

import com.oxymium.si2gassistant.domain.entities.Person

data class PersonListState(
    val personList: List<Person> = emptyList(),
    val selectedPerson: Person? = null,
    val isBottomSheetOpen: Boolean = false
)