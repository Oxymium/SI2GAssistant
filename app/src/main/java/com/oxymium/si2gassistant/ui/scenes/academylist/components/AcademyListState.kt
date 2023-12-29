package com.oxymium.si2gassistant.ui.scenes.academylist.components

import com.oxymium.si2gassistant.domain.entities.Academy

data class AcademyListState(
    val academyList: List<Academy> = emptyList(),
    val selectedAcademy: Academy? = null
)