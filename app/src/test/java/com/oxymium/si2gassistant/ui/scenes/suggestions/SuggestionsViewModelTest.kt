package com.oxymium.si2gassistant.ui.scenes.suggestions

import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import com.oxymium.si2gassistant.utils.TestCoroutineRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

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

}