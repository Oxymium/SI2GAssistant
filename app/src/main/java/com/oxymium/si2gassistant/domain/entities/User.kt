package com.oxymium.si2gassistant.domain.entities

// --------
// APP USER
// --------
class User(
    val id: String?,
    val hasAdministrativeRights: Boolean = false
)

{ constructor() : this(null, false) }
