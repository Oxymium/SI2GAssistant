package com.oxymium.si2gassistant.ui.scenes.buglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.domain.usecase.BugTicketFilter
import com.oxymium.si2gassistant.domain.usecase.BugTicketListEvent
import com.oxymium.si2gassistant.domain.usecase.BugTicketListState
import com.oxymium.si2gassistant.domain.entities.BugTicketPriority
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.loadingInMillis
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BugTicketViewModel(private val bugTicketsRepository: BugTicketRepository): ViewModel() {

    private val _state = MutableStateFlow(BugTicketListState())
    private val _filter = MutableStateFlow<BugTicketFilter>(BugTicketFilter.DefaultValue)
    private val _selected = MutableStateFlow<BugTicket?>(null)

    val state = combine(
        _state, _filter, _selected
    ) { state, filter, selected ->
        state.copy(
            bugTickets = when (filter) {
                BugTicketFilter.DefaultValue -> state.bugTickets.sortedBy { it.submittedDate }.reversed() // sort by chronological order
                BugTicketFilter.LowPriority -> state.bugTickets.filter { it.priority?.name == BugTicketPriority.LOW.name }
                BugTicketFilter.MediumPriority -> state.bugTickets.filter { it.priority?.name == BugTicketPriority.MEDIUM.name }
                BugTicketFilter.HighPriority -> state.bugTickets.filter { it.priority?.name == BugTicketPriority.HIGH.name }
                BugTicketFilter.CriticalPriority -> state.bugTickets.filter { it.priority?.name == BugTicketPriority.CRITICAL.name }
                BugTicketFilter.Resolved -> state.bugTickets.filter { it.isResolved }
                is BugTicketFilter.Search -> state.bugTickets.filter {
                    it.academy?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.description?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.shortDescription?.contains(filter.search ?: "", ignoreCase = true) == true
                }
            },
            selectedBugTicket = state.bugTickets.firstOrNull{ it.id == selected?.id },
            isSelectedBugTicketDetailsOpen = selected != null,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), BugTicketListState())

    init {
        getAllBugTickets()
    }

    private fun getAllBugTickets() {
        viewModelScope.launch {
            bugTicketsRepository.getAllBugTickets().collect {
                when (it) {
                    is Result.Failed -> _state.emit(
                        state.value.copy(
                            isBugTicketsFailed = true,
                            bugTicketsFailedMessage = it.errorMessage
                        )
                    )

                    is Result.Loading ->  { _state.emit(
                            state.value.copy(
                            isBugTicketsLoading = true
                        )
                    )
                        delay(loadingInMillis)

                    }

                    is Result.Success -> _state.emit(
                        state.value.copy(
                            isBugTicketsLoading = false,
                            isBugTicketsFailed = false,
                            bugTickets = it.data
                        ))
                }
            }
        }
    }

    private fun resolveBugTicket(bugTicket: BugTicket?) {
        viewModelScope.launch {
            bugTicket?.let {
                val result = bugTicketsRepository.updateBugTicket(bugTicket).await()
                when (result) {
                    "SUCCESS" -> {
                        val newState = state.value.copy(
                            selectedBugTicket = bugTicket.copy(isResolved = true)
                        )
                        _state.emit(newState)
                    }
                    "FAILURE" -> {}
                }
            }
        }
    }

    private fun updateSelectedBugTicket(bugTicket: BugTicket?) {
        viewModelScope.launch {
            _selected.emit(bugTicket)
        }
    }

    private fun updateBugTicketFilter(bugTicketFilter: BugTicketFilter) {
        viewModelScope.launch {
            _filter.emit(bugTicketFilter)
        }
    }

    fun onEvent(event: BugTicketListEvent) {
        when(event) {
            is BugTicketListEvent.SelectBugTicket ->  updateSelectedBugTicket(event.bugTicket)
            BugTicketListEvent.DismissBugTicketDetailsSheet -> updateSelectedBugTicket(null)
            BugTicketListEvent.OnRefreshButtonClick -> updateBugTicketFilter(BugTicketFilter.DefaultValue) // Refresh
            BugTicketListEvent.OnLowPriorityButtonClick -> updateBugTicketFilter(BugTicketFilter.LowPriority)
            BugTicketListEvent.OnMediumPriorityButtonClick -> updateBugTicketFilter(BugTicketFilter.MediumPriority)
            BugTicketListEvent.OnHighPriorityButtonClick -> updateBugTicketFilter(BugTicketFilter.HighPriority)
            BugTicketListEvent.OnCriticalPriorityButtonClick -> updateBugTicketFilter(BugTicketFilter.CriticalPriority)
            BugTicketListEvent.GenerateRandomBugTicket -> pushRandomBugTickets()
            is BugTicketListEvent.OnResolvedDetailsSheetButtonClick -> resolveBugTicket(event.bugTicket)
            is BugTicketListEvent.OnSearchTextInput -> updateBugTicketFilter(BugTicketFilter.Search(event.search))
        }
    }

    // TODO: remove after testing
    // TESTING
    private fun pushRandomBugTickets(){
        viewModelScope.launch {
            bugTicketsRepository.createBugTicket(provideRandomBugTicket()) // don't need implement deferred result for testing
        }
    }
}