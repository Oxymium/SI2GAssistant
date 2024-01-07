package com.oxymium.si2gassistant.domain.validators

import com.oxymium.si2gassistant.domain.entities.BugTicket

object ReportBugValidator {

    fun validateBugTicket(bugTicket: BugTicket): BugTicketValidationData {
        return BugTicketValidationData(
            bugTicketCategoryError = bugTicket.category?.name?.isBlank() ?: true,
            bugTicketPriorityError = bugTicket.priority?.name?.isBlank() ?: true,
            bugTicketShortDescriptionError = bugTicket.shortDescription.isNullOrEmpty(),
            bugTicketDescriptionError = bugTicket.description.isNullOrEmpty()
        )

    }
}

data class BugTicketValidationData(
    val bugTicketCategoryError: Boolean,
    val bugTicketPriorityError: Boolean,
    val bugTicketShortDescriptionError: Boolean,
    val bugTicketDescriptionError: Boolean
) {
    fun hasErrors(): Boolean {
        return bugTicketCategoryError || bugTicketPriorityError || bugTicketShortDescriptionError || bugTicketDescriptionError
    }
}