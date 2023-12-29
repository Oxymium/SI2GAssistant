package com.oxymium.si2gassistant.ui.scenes.persons.components

import com.oxymium.si2gassistant.ui.scenes.suggestions.components.SuggestionFilter

sealed class PersonFilter {

    data object DefaultFilter: PersonFilter()

    data class Search(val search: String? = null) : PersonFilter()

}