package com.oxymium.si2gassistant.domain.usecase

sealed class BugTicketFilter {

    data object DefaultValue: BugTicketFilter()
    data object LowPriority: BugTicketFilter()
    data object MediumPriority: BugTicketFilter()
    data object HighPriority : BugTicketFilter()
    data object CriticalPriority: BugTicketFilter()
    data object Resolved: BugTicketFilter()

    data class Search(val search: String? = null) : BugTicketFilter()

}