package com.oxymium.si2gassistant.domain.entities

import com.google.firebase.firestore.PropertyName

// --------
// APP USER
// --------
class User(
    val mail: String?,
    val academy: String?,
    val firstname: String?,
    val lastname: String?,
    @PropertyName("hasAdministrativeRights")
    val hasAdministrativeRights: Boolean
)

{ constructor() : this(null, null, null, null, false) }
