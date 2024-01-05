package com.oxymium.si2gassistant.ui.scenes.submitsuggestion

sealed interface SubmitSuggestionEvent {
    data class OnSuggestionSubjectChange(val suggestionSubject: String): SubmitSuggestionEvent
    data class OnSuggestionBodyChange(val suggestionBody: String): SubmitSuggestionEvent
    data object OnSubmitSuggestionButtonClick: SubmitSuggestionEvent
}