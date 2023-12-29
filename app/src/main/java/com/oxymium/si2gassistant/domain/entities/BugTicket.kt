package com.oxymium.si2gassistant.domain.entities

import com.google.firebase.firestore.PropertyName

data class BugTicket(
    var id: String? = null,
    val priority: BugTicketPriority? = null,
    val category: BugTicketCategory? = null,
    val shortDescription: String?,
    val description: String?,
    val academy: String?,
    val submittedBy: String?,
    val submittedDate: Long?,
    @PropertyName("resolved")
    val isResolved: Boolean,
    val resolvedDate: Long?,
    val resolvedComment: String?
)

{
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        false,
        null,
        null)
}

