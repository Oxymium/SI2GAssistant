package com.oxymium.si2gassistant.ui.scenes.suggestions.components

import com.oxymium.si2gassistant.domain.usecase.BugTicketFilter

sealed class SuggestionFilter {

    data object DefaultValue: SuggestionFilter()
    data class Search(val search: String? = null) : SuggestionFilter()

}