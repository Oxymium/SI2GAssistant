package com.oxymium.si2gassistant.domain.filters

sealed class PersonFilter {

    data object DefaultFilter: PersonFilter()

    data class Search(val search: String? = null) : PersonFilter()

}