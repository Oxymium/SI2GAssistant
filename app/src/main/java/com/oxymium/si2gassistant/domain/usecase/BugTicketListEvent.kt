package com.oxymium.si2gassistant.domain.usecase

import com.oxymium.si2gassistant.domain.entities.BugTicket

sealed interface BugTicketListEvent {
    data class SelectBugTicket(val bugTicket: BugTicket): BugTicketListEvent
}