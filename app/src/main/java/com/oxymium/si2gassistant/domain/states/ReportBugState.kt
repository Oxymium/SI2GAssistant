package com.oxymium.si2gassistant.domain.states

import com.oxymium.si2gassistant.domain.entities.BugTicket

data class ReportBugState(
    val bugTickets: List<BugTicket> = emptyList(),
    val submitBugTicketMode: Boolean = false,
    val bugTicketsMode: Boolean = true, // default screen
    val isCategoryFieldError: Boolean = false,
    val isPriorityFieldError: Boolean = false,
    val isShortDescriptionFieldError: Boolean = false,
    val isDescriptionFieldError: Boolean = false,
    // Bug tickets state ----------------------------
    val isBugTicketsFailure: Boolean = false,
    val bugTicketsFailureMessage: String? = null,
    val isBugTicketsLoading: Boolean = false,
    // Submit bug ticket state ----------------------
    val isSubmitBugTicketFailure: Boolean = false,
    val submitBugTicketFailureMessage: String? = null,
    val isSubmitBugTicketLoading: Boolean = false
)