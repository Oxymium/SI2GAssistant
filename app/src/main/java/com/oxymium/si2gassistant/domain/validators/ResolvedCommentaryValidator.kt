package com.oxymium.si2gassistant.domain.validators

object ResolvedCommentaryValidator {

    fun validateResolvedCommentary(resolvedCommentary: String): ResolvedCommentaryValidationData {

        return ResolvedCommentaryValidationData(
            resolvedCommentaryError = resolvedCommentary.isEmpty()
        )

    }

}

data class ResolvedCommentaryValidationData(
    val resolvedCommentaryError: Boolean
) {
    fun hasErrors(): Boolean {
        return resolvedCommentaryError
    }
}