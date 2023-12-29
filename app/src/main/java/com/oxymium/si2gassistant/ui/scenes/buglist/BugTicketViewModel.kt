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
        _state, bugTicketsRepository.getAllBugTickets(), _filter, _selected
    ) { state, bugTickets, filter, selected ->
        state.copy(
            bugTickets = when (filter) {
                BugTicketFilter.DefaultValue -> bugTickets
                BugTicketFilter.LowPriority -> bugTickets.filter { it.priority?.name == BugTicketPriority.LOW.name }
                BugTicketFilter.MediumPriority -> bugTickets.filter { it.priority?.name == BugTicketPriority.MEDIUM.name }
                BugTicketFilter.HighPriority -> bugTickets.filter { it.priority?.name == BugTicketPriority.HIGH.name }
                BugTicketFilter.CriticalPriority -> bugTickets.filter { it.priority?.name == BugTicketPriority.CRITICAL.name }
                BugTicketFilter.Resolved -> bugTickets.filter { it.isResolved }
                is BugTicketFilter.Search -> bugTickets.filter {
                    it.academy?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.description?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.shortDescription?.contains(filter.search ?: "", ignoreCase = true) == true
                }
            },
            selectedBugTicket = bugTickets.firstOrNull{ it.id == selected?.id },
            isSelectedBugTicketDetailsOpen = selected != null,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), BugTicketListState())

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