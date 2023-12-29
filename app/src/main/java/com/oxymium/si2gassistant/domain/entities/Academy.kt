package com.oxymium.si2gassistant.domain.entities

data class Academy(
    val id: String,
    val title: String,
    val shortTitle: String
)

{ constructor() : this("", "", "") }