package com.oxymium.si2gassistant.domain.entities

enum class BugTicketCategory {
    TYPO,
    GLITCH,
    UI,
    PERFORMANCE,
    SECURITY,
    OTHER
}

val ALL_BUG_CATEGORIES = listOf(
    BugTicketCategory.TYPO,
    BugTicketCategory.GLITCH,
    BugTicketCategory.UI,
    BugTicketCategory.PERFORMANCE,
    BugTicketCategory.SECURITY,
    BugTicketCategory.OTHER,
    )