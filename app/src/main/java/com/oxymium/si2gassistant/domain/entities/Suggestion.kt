package com.oxymium.si2gassistant.domain.entities

data class Suggestion(
    var id: String?,
    val subject: String?,
    val body: String?,
    val submittedBy: String?,
    val submittedAcademy: String?,
    val submittedDate: Long?
) {
    constructor(): this(null, null, null, null, null, null)
}