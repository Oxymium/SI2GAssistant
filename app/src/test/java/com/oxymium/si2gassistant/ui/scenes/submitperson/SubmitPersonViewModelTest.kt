package com.oxymium.si2gassistant.ui.scenes.submitperson

import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.utils.TestCoroutineRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

class SubmitPersonViewModelTest {

    private val personRepository = mockk<PersonRepository>()

    private lateinit var submitPersonViewModel: SubmitPersonViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        submitPersonViewModel = SubmitPersonViewModel(personRepository)
    }

}