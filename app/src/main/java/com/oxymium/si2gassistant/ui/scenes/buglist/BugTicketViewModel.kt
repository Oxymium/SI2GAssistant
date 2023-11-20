package com.oxymium.si2gassistant.ui.scenes.buglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.domain.usecase.BugTicketListEvent
import com.oxymium.si2gassistant.domain.usecase.BugTicketListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BugTicketViewModel(private val bugTicketsRepository: BugTicketRepository): ViewModel() {

    private val _state = MutableStateFlow(BugTicketListState())
    val state = combine(
        _state, bugTicketsRepository.getAllBugTickets()
    ) {
            state, bugTickets ->
        state.copy(
            bugTickets = bugTickets
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), BugTicketListState())

    fun onEvent(event: BugTicketListEvent) {
        when(event) {
            is BugTicketListEvent.SelectBugTicket -> println(event.bugTicket)
        }

    }
}