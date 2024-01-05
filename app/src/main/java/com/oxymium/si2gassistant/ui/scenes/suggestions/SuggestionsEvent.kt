package com.oxymium.si2gassistant.ui.scenes.suggestions

sealed interface SuggestionsEvent {
    data class OnSearchTextChange(val search: String): SuggestionsEvent
    data object OnRandomSuggestionButtonClick: SuggestionsEvent
}