package com.oxymium.si2gassistant.ui.scenes.bugtickets

import com.oxymium.si2gassistant.domain.entities.BugTicket

sealed interface BugTicketsEvent {
    data class SelectBugTicket(val bugTicket: BugTicket): BugTicketsEvent
    data object DismissBugTicketDetailsSheet: BugTicketsEvent
    data class OnResolvedDetailsSheetButtonClick(val bugTicket: BugTicket): BugTicketsEvent
    data class OnResolvedCommentChange(val resolvedComment: String): BugTicketsEvent
    data class OnSearchTextChange(val search: String): BugTicketsEvent
}