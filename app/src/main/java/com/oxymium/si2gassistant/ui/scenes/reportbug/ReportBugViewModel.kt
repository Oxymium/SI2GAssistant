package com.oxymium.si2gassistant.ui.scenes.reportbug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.currentUser
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.domain.states.ReportBugState
import com.oxymium.si2gassistant.domain.validators.ReportBugValidator
import com.oxymium.si2gassistant.loadingInMillis
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class ReportBugViewModel(
    private val bugTicketRepository: BugTicketRepository
): ViewModel() {

    private val _state = MutableStateFlow(ReportBugState())
    val state = _state.asStateFlow()

    private val _bugTicket = MutableStateFlow(BugTicket())
    private val bugTicket = _bugTicket.asStateFlow()

    init {
        getAllBugTicketsByUser()
    }
    private fun createBugTicket(bugTicket: BugTicket) {
        viewModelScope.launch {
            val bugTicketFinalized = bugTicket.copy(
                submittedDate = Calendar.getInstance().timeInMillis, // attach now to the submittedDate
                academy = currentUser?.academy,
                submittedBy = currentUser?.mail
            )
            bugTicketRepository.submitBugTicket(bugTicketFinalized).collect {
                when (it) {
                    // FAILURE
                    is Result.Failed -> _state.emit(
                        state.value.copy(
                            isSubmitBugTicketFailure = true,
                            submitBugTicketFailureMessage = it.errorMessage
                        )
                    )
                    // LOADING
                    is Result.Loading -> {
                        _state.emit(
                            state.value.copy(
                                isSubmitBugTicketLoading = true
                            )
                        )
                        delay(loadingInMillis)
                    }
                    // SUCCESS
                    is Result.Success -> _state.emit(
                        state.value.copy(
                            isSubmitBugTicketFailure = false,
                            submitBugTicketFailureMessage = null,
                            isSubmitBugTicketLoading = false
                        )
                    )

                }
            }
        }
    }

    private fun getAllBugTicketsByUser() {
        viewModelScope.launch {
            currentUser?.mail?.let {  mail ->
                bugTicketRepository.getBugTicketsByUser(mail).collect {
                    when (it) {
                        // FAILURE
                        is Result.Failed -> _state.emit(
                            state.value.copy(
                                isBugTicketsFailure = true,
                                bugTicketsFailureMessage = it.errorMessage
                            )
                        )
                        // LOADING
                        is Result.Loading -> {
                            _state.emit(
                                state.value.copy(
                                    isBugTicketsLoading = true
                                )
                            )
                            delay(loadingInMillis)
                        }
                        // SUCCESS
                        is Result.Success -> _state.emit(
                            state.value.copy(
                                isBugTicketsFailure = false,
                                bugTicketsFailureMessage = null,
                                isBugTicketsLoading = false,
                                bugTickets = it.data
                            )
                        )

                    }
                }
            }
        }
    }

    fun onEvent(event: ReportBugEvent) {
        when (event) {

            is ReportBugEvent.OnBugCategorySelect -> {
                _bugTicket.value =
                    bugTicket.value.copy(
                        category = event.bugTicketCategory
                    )
            }

            is ReportBugEvent.OnBugPrioritySelect -> {
                _bugTicket.value =
                    bugTicket.value.copy(
                        priority = event.bugTicketPriority
                    )
            }

            is ReportBugEvent.OnShortDescriptionChange -> {
                _bugTicket.value =
                    bugTicket.value.copy(
                        shortDescription = event.shortDescription
                    )
            }

            is ReportBugEvent.OnDescriptionChange -> {
                _bugTicket.value =
                    bugTicket.value.copy(
                        description = event.description
                    )
            }

            ReportBugEvent.OnBugTicketsModeButtonClick -> {
                _state.value =
                    state.value.copy(
                        bugTicketsMode = true,
                        submitBugTicketMode = false
                    )
            }
            ReportBugEvent.OnReportBugModeButtonClick -> {
                _state.value =
                    state.value.copy(
                        bugTicketsMode = false,
                        submitBugTicketMode = true
                    )
            }

            ReportBugEvent.OnReportBugButtonClick -> {

                val result = ReportBugValidator.validateBugTicket(bugTicket.value)

                // Check if any errors
                if (result.hasErrors()) {
                    _state.value =
                        state.value.copy(
                            isCategoryFieldError = result.bugTicketCategoryError ,
                            isPriorityFieldError = result.bugTicketPriorityError,
                            isShortDescriptionFieldError = result.bugTicketShortDescriptionError,
                            isDescriptionFieldError = result.bugTicketDescriptionError
                        )
                } else {
                    createBugTicket(bugTicket.value)
                }

            }

        }
    }
}