package com.oxymium.si2gassistant.ui.scenes.persons

import com.google.common.truth.Truth
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.mock.provideRandomPerson
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.domain.states.PersonListState
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

class PersonsViewModelTest {

    private val personRepository = mockk<PersonRepository>()

    private lateinit var personsViewModel: PersonsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        personsViewModel = PersonsViewModel(personRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on search text change event`() = runTest {
        // GIVEN
        val givenFiler = "gre"
        val state = personsViewModel.state.observe(this)
        val persons = List(5) { provideRandomPerson() }
        every { personRepository.getAllPersons() } returns flow { Result.Success(persons) }

        // WHEN
        personsViewModel.onEvent(
            PersonsEvent.OnSearchTextChange(givenFiler)
        )
        advanceUntilIdle()

        // THEN
        val filteredPersons = persons.filter { it.academy?.contains(givenFiler, ignoreCase = true) == true }
        Truth.assertThat(state.values).containsExactly(
            PersonListState().copy( persons = filteredPersons)
        )

        state.finish()
    }

}