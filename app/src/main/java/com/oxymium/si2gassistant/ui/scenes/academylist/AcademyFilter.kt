package com.oxymium.si2gassistant.ui.scenes.academylist

sealed class AcademyFilter {

    data object Default: AcademyFilter()
    data class Alphabetically(var isAscendingSorting: Boolean = true): AcademyFilter()
    data class Numerically(var isAscendingSorting: Boolean = true): AcademyFilter()

}