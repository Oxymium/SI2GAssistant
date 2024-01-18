package com.oxymium.si2gassistant.ui.scenes.suggestions

import com.google.common.truth.Truth
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.Suggestion
import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import com.oxymium.si2gassistant.domain.states.SuggestionsState
import com.oxymium.si2gassistant.utils.TestCoroutineRule
import com.oxymium.si2gassistant.utils.observe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SuggestionsViewModelTest {

    private val suggestionRepository = mockk<SuggestionRepository>()

    private lateinit var suggestionsViewModel: SuggestionsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        suggestionsViewModel = SuggestionsViewModel(suggestionRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get all suggestions failure test`() = runTest {
        // GIVEN
        val state = suggestionsViewModel.state.observe(this)
        coEvery { suggestionRepository.getAllSuggestions() } returns flow { Result.Failed("error", null) }

        // WHEN
        suggestionsViewModel.getAllSuggestions()
        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            SuggestionsState(
                isSuggestionsFailure = true,
                suggestionsFailureMessage = "error"
            )
        )

    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `On search text change event test`() = runTest {
        // GIVEN
        val givenSearch = "academy"
        val state = suggestionsViewModel.state.observe(this)
        every { suggestionRepository.getAllSuggestions() } returns flow { Result.Success(emptyList<List<Suggestion>>()) }
        // WHEN
        suggestionsViewModel.onEvent(
            SuggestionsEvent.OnSearchTextChange(givenSearch)
        )
        advanceUntilIdle()
        // THEN
        Truth.assertThat(state.values).containsExactly(
            SuggestionsState()
        )
        state.finish()
    }

}