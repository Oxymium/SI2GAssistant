package com.oxymium.si2gassistant.domain.usecase

import com.oxymium.si2gassistant.domain.entities.BugTicket

sealed interface BugTicketListEvent {
    data class SelectBugTicket(val bugTicket: BugTicket): BugTicketListEvent
    data object DismissBugTicketDetailsSheet: BugTicketListEvent

    data class OnResolvedDetailsSheetButtonClick(val bugTicket: BugTicket): BugTicketListEvent
    data object OnRefreshButtonClick: BugTicketListEvent
    data object OnLowPriorityButtonClick: BugTicketListEvent
    data object OnMediumPriorityButtonClick: BugTicketListEvent
    data object OnHighPriorityButtonClick: BugTicketListEvent
    data object OnCriticalPriorityButtonClick: BugTicketListEvent

    data class OnSearchTextInput(val search: String): BugTicketListEvent

}