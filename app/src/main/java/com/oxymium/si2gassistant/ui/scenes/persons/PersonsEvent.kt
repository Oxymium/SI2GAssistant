package com.oxymium.si2gassistant.ui.scenes.persons

sealed interface PersonsEvent {
    data class OnSearchTextChange(val search: String): PersonsEvent
}