package com.oxymium.si2gassistant.ui.scenes.submitsuggestion

import com.google.common.truth.Truth
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.Suggestion
import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import com.oxymium.si2gassistant.domain.states.SubmitSuggestionState
import com.oxymium.si2gassistant.utils.TestCoroutineRule
import com.oxymium.si2gassistant.utils.observe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SubmitSuggestionViewModelTest {

    private val suggestionRepository = mockk<SuggestionRepository>()

    private lateinit var submitSuggestionViewModel: SubmitSuggestionViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        submitSuggestionViewModel = SubmitSuggestionViewModel(suggestionRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on suggestion subject change event`() = runTest {
        // GIVEN
        val givenSubject = "suggestion"
        val suggestion = submitSuggestionViewModel.suggestion.observe(this)
        every { suggestionRepository.getAllSuggestions() } returns flow { Result.Success(emptyList<List<Suggestion>>()) }

        // WHEN
        submitSuggestionViewModel.onEvent(
            SubmitSuggestionEvent.OnSuggestionSubjectChange(givenSubject)
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(suggestion.values).containsExactly(
            Suggestion(),
            Suggestion().copy(subject = givenSubject)
        )

        suggestion.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on suggestion body change event`() = runTest {
        // GIVEN
        val givenBody = "body"
        val suggestion = submitSuggestionViewModel.suggestion.observe(this)
        every { suggestionRepository.getAllSuggestions() } returns flow { Result.Success(emptyList<List<Suggestion>>()) }

        // WHEN
        submitSuggestionViewModel.onEvent(
            SubmitSuggestionEvent.OnSuggestionBodyChange(givenBody)
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(suggestion.values).containsExactly(
            Suggestion(),
            Suggestion().copy(body = givenBody)
        )

        suggestion.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on submit suggestion button click event`() = runTest {
        // GIVEN
        val state = submitSuggestionViewModel.state.observe(this)
        every { suggestionRepository.getAllSuggestions() } returns flow { Result.Success(emptyList<List<Suggestion>>()) }

        // WHEN
        submitSuggestionViewModel.onEvent(
            SubmitSuggestionEvent.OnSubmitSuggestionButtonClick
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            SubmitSuggestionState(),
            SubmitSuggestionState(
                isSubjectFieldError = true,
                isBodyFieldError = true
            )
        )

        state.finish()

    }

}