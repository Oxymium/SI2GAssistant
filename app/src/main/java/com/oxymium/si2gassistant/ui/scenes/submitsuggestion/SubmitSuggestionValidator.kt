package com.oxymium.si2gassistant.ui.scenes.submitsuggestion

import com.oxymium.si2gassistant.domain.entities.Suggestion

object SubmitSuggestionValidator {

    fun validateSuggestion(suggestion: Suggestion): SuggestionValidationResult {

        var result = SuggestionValidationResult()

        if (suggestion.subject.isNullOrEmpty() || suggestion.subject.isBlank()) {
            result = result.copy(
                suggestionSubjectError = "Error: subject cannot be empty")
        }

        if (suggestion.body.isNullOrEmpty() || suggestion.body.isBlank()) {
            result = result.copy(
                suggestionBodyError = "Error: body cannot be empty")
        }

        return result
    }
}

data class SuggestionValidationResult(
    val suggestionSubjectError: String? = null,
    val suggestionBodyError: String? = null
)