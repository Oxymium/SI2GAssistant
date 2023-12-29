package com.oxymium.si2gassistant.domain.entities

enum class BugTicketPriority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

val ALL_BUG_PRIORITIES = listOf(
    BugTicketPriority.LOW,
    BugTicketPriority.MEDIUM,
    BugTicketPriority.HIGH,
    BugTicketPriority.CRITICAL
)