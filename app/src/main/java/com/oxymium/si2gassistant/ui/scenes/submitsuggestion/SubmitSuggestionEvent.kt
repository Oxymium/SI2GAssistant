package com.oxymium.si2gassistant.ui.scenes.submitsuggestion

sealed interface SubmitSuggestionEvent {

    data class OnSuggestionSubjectChanged(val suggestionSubject: String): SubmitSuggestionEvent
    data class OnSuggestionBodyChanged(val suggestionBody: String): SubmitSuggestionEvent

    data object OnSubmitSuggestionButtonClicked: SubmitSuggestionEvent
}