package com.oxymium.si2gassistant.domain.entities

enum class BugTicketCategory {
    NAVIGATION,
    DESIGN,
    TYPOGRAPHY,
    PERFORMANCE,
    INCONSISTENCY,
    ACCESSIBILITY,
    GLITCHES,
    SECURITY
}

val ALL_BUG_CATEGORIES = listOf(
    BugTicketCategory.NAVIGATION,
    BugTicketCategory.DESIGN,
    BugTicketCategory.TYPOGRAPHY,
    BugTicketCategory.PERFORMANCE,
    BugTicketCategory.INCONSISTENCY,
    BugTicketCategory.ACCESSIBILITY,
    BugTicketCategory.GLITCHES,
    BugTicketCategory.SECURITY
    )