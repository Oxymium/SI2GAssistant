package com.oxymium.si2gassistant.domain.entities

data class Announcement(
    val id: String?,
    val title: String?,
    val content: String?,
    val submittedDate: Long?,
) {
    constructor() : this(
        null,
        null,
        null,
        null,
    )

}

