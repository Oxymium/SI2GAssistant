package com.oxymium.si2gassistant.domain.entities

data class Module(
    val id: String?,
    val title: String?,
    val content: String?
)

{ constructor() : this(null, null, null) }

