package com.oxymium.si2gassistant.domain.entities

data class Message(
    var id: String?,
    val submittedDate: Long?,
    val submittedBy: String?,
    val content: String?
)

{
    constructor() : this(
        null,
        null,
        null,
        null
    )

}