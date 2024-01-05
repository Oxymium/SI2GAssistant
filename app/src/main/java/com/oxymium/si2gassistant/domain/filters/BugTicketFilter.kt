package com.oxymium.si2gassistant.domain.filters

sealed class BugTicketFilter {

    data object DefaultValue: BugTicketFilter()
    data class Search(val search: String? = null) : BugTicketFilter()

}