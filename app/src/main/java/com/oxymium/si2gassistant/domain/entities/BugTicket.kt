package com.oxymium.si2gassistant.domain.entities

import com.google.firebase.firestore.PropertyName
import com.oxymium.si2gassistant.ui.theme.BugTicketCategory
import com.oxymium.si2gassistant.ui.theme.BugTicketPriority

data class BugTicket(
    val id: String?,
    val priority: BugTicketPriority,
    val category: BugTicketCategory,
    val shortDescription: String?,
    val description: String?,
    val academy: String?,
    val submittedDate: Long?,
    @PropertyName("resolved")
    val isResolved: Boolean,
    val resolvedDate: Long?,
    val resolvedComment: String?
)

{
    constructor() : this(
        null,
        BugTicketPriority.CRITICAL,
        BugTicketCategory.GLITCH,
        null,
        null,
        null,
        null,
        false,
        null,
        null)
}

