package com.oxymium.si2gassistant.domain.validators

import com.oxymium.si2gassistant.domain.entities.BugTicket

object ReportBugValidator {

    fun validateBugTicket(bugTicket: BugTicket): BugTicketValidationResult {

        var result = BugTicketValidationResult()

        if (bugTicket.category?.name.isNullOrEmpty()) {
            result = result.copy(
                bugTicketCategoryError = "Error: category cannot be empty")
        }

        if (bugTicket.priority?.name.isNullOrEmpty()) {
            result = result.copy(
                bugTicketPriorityError = "Error: priority cannot be empty")
        }

        if (bugTicket.shortDescription.isNullOrEmpty() || bugTicket.shortDescription.isBlank()) {
            result = result.copy(
                bugTicketShortDescriptionError = "Error: short description cannot be empty")
        }

        if (bugTicket.description.isNullOrEmpty() || bugTicket.description.isBlank()) {
            result = result.copy(
                bugTicketDescriptionError = "Error: description cannot be empty")
        }

        return result
    }
}

data class BugTicketValidationResult(
    val bugTicketCategoryError: String? = null,
    val bugTicketPriorityError: String? = null,
    val bugTicketShortDescriptionError: String? = null,
    val bugTicketDescriptionError: String? = null
)