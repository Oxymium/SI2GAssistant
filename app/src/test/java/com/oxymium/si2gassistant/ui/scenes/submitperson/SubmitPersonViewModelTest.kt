package com.oxymium.si2gassistant.ui.scenes.submitperson

import com.google.common.truth.Truth
import com.oxymium.si2gassistant.domain.entities.Message
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.domain.states.SubmitPersonState
import com.oxymium.si2gassistant.ui.scenes.chat.ChatEvent
import com.oxymium.si2gassistant.ui.scenes.chat.ChatState
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on person role change event`() = runTest {
        // GIVEN
        val givenRole = "Marketing leader"
        val person = submitPersonViewModel.person.observe(this)
        every { personRepository.getAllPersons() } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        submitPersonViewModel.onEvent(
            SubmitPersonEvent.OnPersonRoleChange(givenRole)
        )
        advanceUntilIdle()

        // THEN
        val expectedPerson = Person().copy(role = givenRole)
        Truth.assertThat(person.values).containsExactly(
            Person(),
            expectedPerson
        )

        person.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on person first name change event`() = runTest {
        // GIVEN
        val givenFirstname = "Jon"
        val person = submitPersonViewModel.person.observe(this)
        every { personRepository.getAllPersons() } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        submitPersonViewModel.onEvent(
            SubmitPersonEvent.OnPersonFirstNameChange(givenFirstname)
        )
        advanceUntilIdle()

        // THEN
        val expectedPerson = Person().copy(firstname = givenFirstname)
        Truth.assertThat(person.values).containsExactly(
            Person(),
            expectedPerson
        )

        person.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on person last name change event`() = runTest {
        // GIVEN
        val givenLastname = "Doe"
        val person = submitPersonViewModel.person.observe(this)
        every { personRepository.getAllPersons() } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        submitPersonViewModel.onEvent(
            SubmitPersonEvent.OnPersonLastNameChange(givenLastname)
        )
        advanceUntilIdle()

        // THEN
        val expectedPerson = Person().copy(lastname = givenLastname)
        Truth.assertThat(person.values).containsExactly(
            Person(),
            expectedPerson
        )

        person.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on person mode button click event`() = runTest {
        // GIVEN
        val state = submitPersonViewModel.state.observe(this)
        every { personRepository.getAllPersons() } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        submitPersonViewModel.onEvent(
            SubmitPersonEvent.OnPersonsModeButtonClick
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            SubmitPersonState(
                submitPersonMode = false,
                personsMode = true
            )
        )

        state.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on submit person mode button click event`() = runTest {
        // GIVEN
        val state = submitPersonViewModel.state.observe(this)
        every { personRepository.getAllPersons() } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        submitPersonViewModel.onEvent(
            SubmitPersonEvent.OnSubmitPersonModeButtonClick
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            SubmitPersonState(),
            SubmitPersonState(
                submitPersonMode = true,
                personsMode = false
            )
        )

        state.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on dismiss person details sheet event`() = runTest {
        // GIVEN
        val state = submitPersonViewModel.state.observe(this)
        every { personRepository.getAllPersons() } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        submitPersonViewModel.onEvent(
            SubmitPersonEvent.DismissPersonDetailsSheet
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            SubmitPersonState(
               selectedPerson = null
            )
        )

        state.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on submit person button click event`() = runTest {
        // GIVEN
        val state = submitPersonViewModel.state.observe(this)
        every { personRepository.getAllPersons() } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        submitPersonViewModel.onEvent(
            SubmitPersonEvent.OnSubmitPersonButtonClick
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            SubmitPersonState(
                selectedPerson = null
            )
        )

        state.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on person modules switch toggle event`() = runTest {
    }

}