package com.oxymium.si2gassistant.ui.scenes.suggestions

import com.oxymium.si2gassistant.domain.entities.Suggestion

data class SuggestionsState(
    val suggestions: List<Suggestion> = emptyList()
)