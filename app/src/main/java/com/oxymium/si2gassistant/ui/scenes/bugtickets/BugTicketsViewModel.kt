package com.oxymium.si2gassistant.ui.scenes.bugtickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.domain.filters.BugTicketFilter
import com.oxymium.si2gassistant.domain.states.BugTicketsState
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.loadingInMillis
import com.oxymium.si2gassistant.domain.validators.ResolvedCommentaryValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class BugTicketsViewModel(
    private val bugTicketsRepository: BugTicketRepository
): ViewModel() {

    private val _state = MutableStateFlow(BugTicketsState())
    private val _filter = MutableStateFlow<BugTicketFilter>(BugTicketFilter.DefaultValue)
    private val _selected = MutableStateFlow<BugTicket?>(null)

    val state = combine(
        _state,
        _filter,
        _selected
    ) { state, filter, selected ->
        state.copy(
            bugTickets = when (filter) {
                BugTicketFilter.DefaultValue -> state.bugTickets.sortedBy { it.submittedDate }.reversed() // sort by chronological order
                is BugTicketFilter.Search -> state.bugTickets.filter {
                    it.academy?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.description?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.shortDescription?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.category?.name?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.priority?.name?.contains(filter.search ?: "", ignoreCase = true) == true
                }
            },
            selectedBugTicket = state.bugTickets.firstOrNull{ it.id == selected?.id },
            isSelectedBugTicketDetailsOpen = selected != null,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        BugTicketsState()
    )

    init {
        getAllBugTickets()
    }

    private fun getAllBugTickets() {
        viewModelScope.launch {
            bugTicketsRepository.getAllBugTickets().collect {
                when (it) {

                    // FAILURE
                    is Result.Failed ->
                        _state.emit(
                            state.value.copy(
                                isBugTicketsFailed = true,
                                bugTicketsFailedMessage = it.errorMessage
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
                    is Result.Success ->
                        _state.emit(
                            state.value.copy(
                            isBugTicketsLoading = false,
                            isBugTicketsFailed = false,
                            bugTickets = it.data
                            )
                        )

                }
            }
        }

    }

    private fun updateBugTicket(bugTicket: BugTicket) {
        viewModelScope.launch {
            val bugTicketUpdated = state.value.copy(
                selectedBugTicket = bugTicket.copy(
                    isResolved = true,
                    resolvedDate = Calendar.getInstance().timeInMillis,
                    resolvedComment = state.value.resolvedComment
                )
            )
            bugTicketUpdated.selectedBugTicket?.let {
                bugTicketsRepository.updateBugTicket(it).collect { result ->
                    when (result) {

                        // FAILURE
                        is Result.Failed -> _state.emit(
                            state.value.copy(
                                isUpdateBugTicketFailure = true,
                                isUpdateBugTicketFailureMessage = result.errorMessage
                            )
                        )

                        // LOADING
                        is Result.Loading -> { _state.emit(
                            state.value.copy(
                                isUpdateBugTicketLoading = true
                            )
                        )
                            delay(loadingInMillis)
                        }

                        // SUCCESS
                        is Result.Success -> _state.emit(
                            state.value.copy(
                                isUpdateBugTicketFailure = false,
                                isUpdateBugTicketFailureMessage = null,
                                isUpdateBugTicketLoading = false,
                            )
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: BugTicketsEvent) {
        when(event) {

            is BugTicketsEvent.SelectBugTicket -> _selected.value = event.bugTicket

            BugTicketsEvent.DismissBugTicketDetailsSheet -> _selected.value = null

            is BugTicketsEvent.OnResolvedCommentChange -> {
                _state.value =
                    state.value.copy(
                        resolvedComment = event.resolvedComment
                    )
            }

            is BugTicketsEvent.OnResolvedDetailsSheetButtonClick -> {

                // Get resolved comment
                val resolvedComment = state.value.resolvedComment ?: ""

                val result = ResolvedCommentaryValidator.validateResolvedCommentary(resolvedComment)

                // Verify if any element is null
                val errors = listOfNotNull(
                    result.resolvedCommentaryError
                )

                if (errors.isEmpty()) {
                    _state.value = state.value.copy(
                        isResolvedCommentFieldError = false
                    )

                    // Update bug ticket if conditions are met
                    state.value.copy().let {
                        state.value.selectedBugTicket?.let { bugTicket ->
                            updateBugTicket(
                                bugTicket
                            )
                        }
                    }

                } else {

                    if (!result.resolvedCommentaryError.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            isResolvedCommentFieldError = true
                        )
                    }

                }
            }

            is BugTicketsEvent.OnSearchTextChange -> {
                _filter.value = BugTicketFilter.Search(event.search)
            }

        }
    }

}