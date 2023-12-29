package com.oxymium.si2gassistant.ui.scenes.suggestions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.BugTicketPriority
import com.oxymium.si2gassistant.domain.entities.Suggestion
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomSuggestion
import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import com.oxymium.si2gassistant.domain.usecase.BugTicketFilter
import com.oxymium.si2gassistant.domain.usecase.BugTicketListState
import com.oxymium.si2gassistant.ui.scenes.suggestions.components.SuggestionFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SuggestionsViewModel(
    private val suggestionRepository: SuggestionRepository
): ViewModel() {

    private val _state = MutableStateFlow(SuggestionsState())
    private val _filter = MutableStateFlow<SuggestionFilter>(SuggestionFilter.DefaultValue)

    val state = combine(
        _state, suggestionRepository.getAllSuggestions(), _filter
    ) { state, suggestions, filter ->
        state.copy(
            suggestions = when (filter) {
                SuggestionFilter.DefaultValue -> suggestions
                is SuggestionFilter.Search -> suggestions.filter {
                    it.body?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.subject?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.submittedBy?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.submittedAcademy?.contains(filter.search ?: "", ignoreCase = true) == true
                }
            },
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SuggestionsState())


    private fun updateSuggestionFilter(suggestionFilter: SuggestionFilter) {
        viewModelScope.launch {
            _filter.emit(suggestionFilter)
        }
    }

    private fun pushRandomSuggestion(suggestion: Suggestion) {
        viewModelScope.launch {
            suggestionRepository.submitSuggestion(suggestion)
        }
    }

    fun onEvent(event: SuggestionsEvent) {
        when (event) {
            is SuggestionsEvent.OnSearchTextInput -> updateSuggestionFilter(SuggestionFilter.Search(event.search))
            SuggestionsEvent.OnRandomSuggestionButtonClicked -> pushRandomSuggestion(provideRandomSuggestion())

        }

    }

}