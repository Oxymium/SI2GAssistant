package com.oxymium.si2gassistant.ui.scenes.reportbug

import com.oxymium.si2gassistant.domain.entities.BugTicketCategory
import com.oxymium.si2gassistant.domain.entities.BugTicketPriority

sealed interface ReportBugEvent {

    data object OnBugTicketsModeButtonClicked: ReportBugEvent
    data object OnReportBugModeButtonClicked: ReportBugEvent

    data class OnBugCategorySelected(val bugTicketCategory: BugTicketCategory): ReportBugEvent
    data class OnBugPrioritySelected(val bugTicketPriority: BugTicketPriority): ReportBugEvent

    data class OnShortDescriptionChanged(val shortDescription: String): ReportBugEvent
    data class OnDescriptionChanged(val description: String): ReportBugEvent

    data object OnReportBugButtonClicked: ReportBugEvent
}