package com.oxymium.si2gassistant.ui.scenes.buglist.components

object ResolvedCommentaryValidator {

    fun validateResolvedCommentary(resolvedCommentary: String): ResolvedCommentaryValidationResult {

        var result = ResolvedCommentaryValidationResult()

        if (resolvedCommentary.isEmpty() || resolvedCommentary.isBlank()) {
            result = result.copy(
                resolvedCommentaryError = "Error: resolved commentary cannot be empty")
        }

        return result
    }

}

data class ResolvedCommentaryValidationResult(
    val resolvedCommentaryError: String? = null
)