package com.oxymium.si2gassistant.ui.scenes.persons.components

import com.oxymium.si2gassistant.domain.entities.Person

sealed interface PersonListEvent {

    data class OnPersonClick(val person: Person): PersonListEvent

    data class OnSearchTextInput(val search: String): PersonListEvent

    data object OnRandomPersonButtonClicked: PersonListEvent

}