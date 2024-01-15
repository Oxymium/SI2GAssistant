package com.oxymium.si2gassistant.domain.states

import com.oxymium.si2gassistant.domain.entities.BugTicket

data class BugTicketsState(
    val bugTickets: List<BugTicket> = emptyList(),
    val selectedBugTicket: BugTicket? = null,
    val isSelectedBugTicketDetailsOpen: Boolean = false,
    // Resolved comment ---------------------
    val resolvedComment: String? = null,
    val isResolvedCommentFieldError: Boolean = false,
    // Bug tickets state --------------------
    val isBugTicketsLoading: Boolean = false,
    val isBugTicketsFailed: Boolean = false,
    val bugTicketsFailedMessage: String? = null,
    // Update bug ticket state --------------
    val isUpdateBugTicketFailure: Boolean = false,
    val updateBugTicketFailureMessage: String? = null,
    val isUpdateBugTicketLoading: Boolean = false
)
