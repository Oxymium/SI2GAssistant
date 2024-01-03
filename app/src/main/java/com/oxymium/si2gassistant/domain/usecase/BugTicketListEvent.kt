package com.oxymium.si2gassistant.domain.usecase

import com.oxymium.si2gassistant.domain.entities.BugTicket

sealed interface BugTicketListEvent {
    data class SelectBugTicket(val bugTicket: BugTicket): BugTicketListEvent
    data object DismissBugTicketDetailsSheet: BugTicketListEvent
    data class OnResolvedDetailsSheetButtonClick(val bugTicket: BugTicket): BugTicketListEvent
    data class OnResolvedCommentChanged(val resolvedComment: String): BugTicketListEvent
    data class OnSearchTextInput(val search: String): BugTicketListEvent
}