package com.oxymium.si2gassistant.domain.usecase

import com.oxymium.si2gassistant.domain.entities.Academy

sealed interface AcademyListEvent {

    data class SelectAcademy(val academy: Academy): AcademyListEvent

    data object OnAlphabeticallyFilterButtonClick: AcademyListEvent
    data object OnNumericallyFilterButtonClick: AcademyListEvent

}