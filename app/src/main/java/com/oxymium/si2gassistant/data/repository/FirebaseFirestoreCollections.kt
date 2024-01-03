package com.oxymium.si2gassistant.data.repository

// Firestore's collections' names
object FirebaseFirestoreCollections {
    const val ACADEMIES = "academies"
    const val BUG_TICKETS = "bug_tickets"
    const val SUGGESTIONS = "suggestions"
    const val PERSONS = "persons"
    const val USERS = "users"
    const val MODULES = "modules"
}

object FirebaseFirestoreFields {
    const val ACADEMY_ID = "academyId"
    const val USER_ID = "userId"
    const val MAIL = "mail"
    const val RESOLVED = "resolved"
    const val RESOLVED_COMMENT = "resolvedComment"
    const val RESOLVED_DATE = "resolvedDate"
}