package com.oxymium.si2gassistant.domain.usecase

import com.oxymium.si2gassistant.domain.entities.BugTicket

data class BugTicketListState(
    val bugTickets: List<BugTicket> = emptyList(),
    val selectedBugTicket: BugTicket? = null,
    val isSelectedBugTicketDetailsOpen: Boolean = false,
    // Bug tickets state --------------------
    val isBugTicketsLoading: Boolean = false,
    val isBugTicketsFailed: Boolean = false,
    val bugTicketsFailedMessage: String? = null,
    // Update bug ticket state --------------
    val isUpdateBugTicketFailure: Boolean = false,
    val isUpdateBugTicketFailureMessage: String? = null,
    val isUpdateBugTicketLoading: Boolean = false
)
