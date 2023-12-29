package com.oxymium.si2gassistant.ui.scenes.suggestions

sealed interface SuggestionsEvent {

    data class OnSearchTextInput(val search: String): SuggestionsEvent

    data object OnRandomSuggestionButtonClicked: SuggestionsEvent
}