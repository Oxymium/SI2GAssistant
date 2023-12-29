package com.oxymium.si2gassistant.ui.scenes.reportbug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.data.repository.GLOBAL_USER
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.ui.scenes.reportbug.components.ReportBugValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class ReportBugViewModel(
    private val bugTicketRepository: BugTicketRepository
): ViewModel() {

    init {
        getAllBugTicketsByUser()
    }

    private val _state = MutableStateFlow(ReportBugState())
    val state = _state.asStateFlow()

    private val _bugTicket = MutableStateFlow(BugTicket())
    private val bugTicket = _bugTicket.asStateFlow()
    private fun createBugTicket(bugTicket: BugTicket) {
        viewModelScope.launch {
            val bugTicketFinalized = bugTicket.copy(
                submittedDate = Calendar.getInstance().timeInMillis, // attach now to the submittedDate
                academy = GLOBAL_USER?.academy,
                submittedBy = GLOBAL_USER?.mail
            )
            bugTicketRepository.createBugTicket(bugTicketFinalized)
        }
    }

    private fun getAllBugTicketsByUser() {
        viewModelScope.launch {
            bugTicketRepository.getBugTicketsByUser(GLOBAL_USER?.mail!!).collect {
                val newState = state.value.copy(bugTickets = it)
                _state.value = newState
            }
        }
    }

    fun onEvent(reportBugEvent: ReportBugEvent) {
        when (reportBugEvent) {
            is ReportBugEvent.OnBugCategorySelected -> {
                val bugTicket = bugTicket.value.copy(
                    category = reportBugEvent.bugTicketCategory
                )
                _bugTicket.value = bugTicket
            }

            is ReportBugEvent.OnBugPrioritySelected -> {
                val bugTicket = bugTicket.value.copy(
                    priority = reportBugEvent.bugTicketPriority
                )
                _bugTicket.value = bugTicket
            }

            is ReportBugEvent.OnShortDescriptionChanged -> {
                val bugTicket = bugTicket.value.copy(
                    shortDescription = reportBugEvent.shortDescription
                )
                _bugTicket.value = bugTicket
            }

            is ReportBugEvent.OnDescriptionChanged -> {
                val bugTicket = bugTicket.value.copy(
                    description = reportBugEvent.description
                )
                _bugTicket.value = bugTicket
            }

            ReportBugEvent.OnBugTicketsModeButtonClicked -> {
                val newState = state.value.copy(
                    bugTicketsMode = true,
                    submitBugTicketMode = false
                )
                _state.value = newState
            }
            ReportBugEvent.OnReportBugModeButtonClicked -> {
                val newState = state.value.copy(
                    bugTicketsMode = false,
                    submitBugTicketMode = true
                )
                _state.value = newState
            }

            ReportBugEvent.OnReportBugButtonClicked -> {

                // Initial reset
                _state.value = state.value.copy(
                    isCategoryFieldError = false,
                    isPriorityFieldError = false,
                    isShortDescriptionFieldError = false,
                    isDescriptionFieldError = false
                )

                val result = ReportBugValidator.validateBugTicket(bugTicket.value)
                // Verify if any element is null
                val errors = listOfNotNull(
                    result.bugTicketCategoryError,
                    result.bugTicketPriorityError,
                    result.bugTicketShortDescriptionError,
                    result.bugTicketDescriptionError
                )

                if (errors.isEmpty()) {
                    _state.value = state.value.copy(
                        isCategoryFieldError = false,
                        isPriorityFieldError = false,
                        isShortDescriptionFieldError = false,
                        isDescriptionFieldError = false,
                    )

                    createBugTicket(bugTicket.value) // Create new bug ticket after validation

                } else {

                    if (!result.bugTicketCategoryError.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            isCategoryFieldError = true
                        )
                    }

                    if (!result.bugTicketPriorityError.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            isPriorityFieldError = true
                        )
                    }

                    if (!result.bugTicketShortDescriptionError.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            isShortDescriptionFieldError = true
                        )
                    }

                    if (!result.bugTicketDescriptionError.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            isDescriptionFieldError = true
                        )
                    }
                }
            }


        }
    }
}