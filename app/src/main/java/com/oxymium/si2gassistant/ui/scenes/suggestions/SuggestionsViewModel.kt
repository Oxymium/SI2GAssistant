package com.oxymium.si2gassistant.ui.scenes.suggestions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.Suggestion
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomSuggestion
import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import com.oxymium.si2gassistant.loadingInMillis
import com.oxymium.si2gassistant.ui.scenes.suggestions.components.SuggestionFilter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SuggestionsViewModel(
    private val suggestionRepository: SuggestionRepository
): ViewModel() {

    private val _state = MutableStateFlow(SuggestionsState())
    private val _filter = MutableStateFlow<SuggestionFilter>(SuggestionFilter.DefaultValue)

    val state = combine(
        _state, _filter
    ) { state, filter ->
        state.copy(
            suggestions = when (filter) {
                SuggestionFilter.DefaultValue -> state.suggestions.sortedBy { it.submittedDate }.reversed() // order chronologically by default
                is SuggestionFilter.Search -> state.suggestions.filter {
                    it.body?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.subject?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.submittedBy?.contains(filter.search ?: "", ignoreCase = true) == true ||
                            it.submittedAcademy?.contains(filter.search ?: "", ignoreCase = true) == true
                }
            },
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SuggestionsState())

    // Need to load after the combine block otherwise state won't be initialized
    init {
        getAllSuggestions()
    }

    private fun updateState(suggestionState: SuggestionsState) {
        viewModelScope.launch {
            _state.emit(suggestionState)
        }
    }

    private fun getAllSuggestions() {
        viewModelScope.launch {
            suggestionRepository.getAllSuggestions().collect {
                when (it) {
                    // FAILURE
                    is Result.Failed ->
                        updateState(
                            state.value.copy(
                                isSuggestionsFailure = true,
                                suggestionsFailureMessage = it.errorMessage
                            )
                        )
                    // LOADING
                    is Result.Loading -> {
                        updateState(
                            state.value.copy(
                                isSuggestionsLoading = true
                            )
                        )
                        delay(loadingInMillis)
                    }
                    // SUCCESS
                    is Result.Success ->
                        updateState(
                            state.value.copy(
                                isSuggestionsLoading = false,
                                isSuggestionsFailure = false,
                                suggestions = it.data
                            )
                        )

                }
            }
        }
    }


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