package com.oxymium.si2gassistant.ui.scenes.submitsuggestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.data.repository.GLOBAL_USER
import com.oxymium.si2gassistant.domain.entities.Suggestion
import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class SubmitSuggestionViewModel(
    private val suggestionRepository: SuggestionRepository
): ViewModel() {

    private val _state = MutableStateFlow(SubmitSuggestionState())
    val state = _state.asStateFlow()

    private val _suggestion = MutableStateFlow(Suggestion())
    private val suggestion = _suggestion.asStateFlow()

    private fun submitSuggestion(suggestion: Suggestion) {
        viewModelScope.launch {
            val suggestionFinalized = suggestion.copy(
                submittedAcademy = GLOBAL_USER?.academy,
                submittedDate = Calendar.getInstance().timeInMillis,
                submittedBy = GLOBAL_USER?.mail
            )
            suggestionFinalized.let { suggestionRepository.submitSuggestion(it) }
        }
    }

    fun onEvent(submitSuggestionEvent: SubmitSuggestionEvent) {
        when (submitSuggestionEvent) {
            is SubmitSuggestionEvent.OnSuggestionSubjectChanged -> {
                val suggestion = suggestion.value.copy(
                    subject = submitSuggestionEvent.suggestionSubject
                )
                _suggestion.value = suggestion
            }
            is SubmitSuggestionEvent.OnSuggestionBodyChanged -> {
                val suggestion = suggestion.value.copy(
                    body = submitSuggestionEvent.suggestionBody
                )
                _suggestion.value = suggestion
            }
            SubmitSuggestionEvent.OnSubmitSuggestionButtonClicked -> {

                // Initial reset
                _state.value = state.value.copy(
                    isSubjectFieldError = false,
                    isBodyFieldError = false
                )

                val result = SubmitSuggestionValidator.validateSuggestion(suggestion.value)
                // Verify if any element is null
                val errors = listOfNotNull(
                    result.suggestionSubjectError,
                    result.suggestionBodyError
                )

                if (errors.isEmpty()) {
                    _state.value = state.value.copy(
                        isSubjectFieldError = false,
                        isBodyFieldError = false,
                    )

                    submitSuggestion(suggestion.value) // Submit new suggestion after validation

                } else {
                    if (!result.suggestionSubjectError.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            isSubjectFieldError = true
                        )
                    }
                    if (!result.suggestionBodyError.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            isBodyFieldError = true
                        )
                    }
                }

            }
        }
    }

}