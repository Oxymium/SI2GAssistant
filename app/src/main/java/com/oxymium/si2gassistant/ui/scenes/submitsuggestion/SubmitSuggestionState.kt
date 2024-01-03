package com.oxymium.si2gassistant.ui.scenes.submitsuggestion

data class SubmitSuggestionState(
    val isSubmitSuggestionFailure: Boolean  = false,
    val isSubmitSuggestionFailureMessage: String? = null,
    val isSubmitSuggestionLoading: Boolean = false,
    val isSubjectFieldError: Boolean = false,
    val isBodyFieldError: Boolean = false
)