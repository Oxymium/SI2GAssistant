package com.oxymium.si2gassistant.ui.scenes.reportbug

import com.oxymium.si2gassistant.domain.entities.BugTicket

data class ReportBugState(
    val bugTickets: List<BugTicket> = emptyList(),
    val submitBugTicketMode: Boolean = false,
    val bugTicketsMode: Boolean = true, // default screen
    val isCategoryFieldError: Boolean = false,
    val isPriorityFieldError: Boolean = false,
    val isShortDescriptionFieldError: Boolean = false,
    val isDescriptionFieldError: Boolean = false
)