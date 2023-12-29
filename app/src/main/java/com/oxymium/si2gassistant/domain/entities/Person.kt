package com.oxymium.si2gassistant.domain.entities

data class Person(
    var id: String?,
    val role: String?,
    val firstname: String?,
    val lastname: String?,
    val validatedModules: String?,
    var academy: String?,
    val user: String?
)

{ constructor() : this(null, null, null, null, null,null, null) }
