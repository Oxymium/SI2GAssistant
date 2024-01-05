package com.oxymium.si2gassistant.ui.scenes.persons

import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.utils.TestCoroutineRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

class PersonListViewModelTest {

    private val personRepository = mockk<PersonRepository>()

    private lateinit var personListViewModel: PersonsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        personListViewModel = PersonsViewModel(personRepository)
    }

}