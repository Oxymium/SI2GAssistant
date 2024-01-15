package com.oxymium.si2gassistant.ui.scenes.reportbug

import com.oxymium.si2gassistant.domain.entities.BugTicketCategory
import com.oxymium.si2gassistant.domain.entities.BugTicketPriority

sealed interface ReportBugEvent {
    data object OnBugTicketsModeButtonClick: ReportBugEvent
    data object OnReportBugModeButtonClick: ReportBugEvent

    data class OnBugCategorySelect(val bugTicketCategory: BugTicketCategory): ReportBugEvent
    data class OnBugPrioritySelect(val bugTicketPriority: BugTicketPriority): ReportBugEvent

    data class OnShortDescriptionChange(val shortDescription: String): ReportBugEvent
    data class OnDescriptionChange(val description: String): ReportBugEvent
    data object OnReportBugButtonClick: ReportBugEvent
}