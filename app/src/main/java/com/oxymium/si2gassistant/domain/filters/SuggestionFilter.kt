package com.oxymium.si2gassistant.domain.filters

sealed class SuggestionFilter {

    data object DefaultValue: SuggestionFilter()
    data class Search(val search: String? = null) : SuggestionFilter()

}