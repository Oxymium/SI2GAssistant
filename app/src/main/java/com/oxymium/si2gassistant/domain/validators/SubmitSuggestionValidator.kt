package com.oxymium.si2gassistant.domain.validators

import com.oxymium.si2gassistant.domain.entities.Suggestion

object SubmitSuggestionValidator {

    fun validateSuggestion(suggestion: Suggestion): SuggestionValidationData {
        return SuggestionValidationData(
            suggestionSubjectError = suggestion.subject.isNullOrEmpty(),
            suggestionBodyError = suggestion.body.isNullOrEmpty()
        )

    }
}

data class SuggestionValidationData(
    val suggestionSubjectError: Boolean,
    val suggestionBodyError: Boolean
) {
    fun hasErrors(): Boolean {
        return suggestionSubjectError || suggestionBodyError
    }
}