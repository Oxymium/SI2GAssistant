package com.oxymium.si2gassistant.ui.scenes.submitsuggestion

import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import com.oxymium.si2gassistant.utils.TestCoroutineRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

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

}