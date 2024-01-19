package com.oxymium.si2gassistant.ui.scenes.metrics

import com.google.common.truth.Truth.assertThat
import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.Module
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Suggestion
import com.oxymium.si2gassistant.domain.entities.User
import com.oxymium.si2gassistant.domain.repository.AcademyRepository
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.domain.repository.ModuleRepository
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import com.oxymium.si2gassistant.domain.repository.UserRepository
import com.oxymium.si2gassistant.domain.states.MetricsState
import com.oxymium.si2gassistant.utils.TestCoroutineRule
import com.oxymium.si2gassistant.utils.observe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MetricsViewModelTest{

    private val bugTicketRepository = mockk<BugTicketRepository>()
    private val academyRepository = mockk<AcademyRepository>()
    private val moduleRepository = mockk<ModuleRepository>()
    private val userRepository = mockk<UserRepository>()
    private val personRepository = mockk<PersonRepository>()
    private val suggestionRepository = mockk<SuggestionRepository>()

    private lateinit var metricsViewModel: MetricsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        metricsViewModel = MetricsViewModel(
            bugTicketRepository = bugTicketRepository,
            academyRepository = academyRepository,
            moduleRepository = moduleRepository,
            userRepository = userRepository,
            personRepository = personRepository,
            suggestionRepository = suggestionRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on bug ticket clicked event`() = runTest {
        // GIVEN
        val state = metricsViewModel.state.observe(this)
       coEvery { bugTicketRepository.getAllBugTickets() } returns flow { emptyList<BugTicket>() }
       coEvery { academyRepository.getAllAcademies() } returns flow { emptyList<Academy>() }
       coEvery { moduleRepository.getAllModules() } returns flow { emptyList<Module>() }
       coEvery { userRepository.getAllUsers() } returns flow { emptyList<User>() }
       coEvery { personRepository.getAllPersons() } returns flow { emptyList<Person>() }
       coEvery { suggestionRepository.getAllSuggestions() } returns flow { emptyList<Suggestion>() }
        // WHEN
        metricsViewModel.onEvent(
            MetricsScreenEvent.OnBugTicketsButtonClick
        )

        advanceUntilIdle()

        // THEN
        assertThat(state.values).containsExactly(
            MetricsState(
                isBugTicketMetricsScreen = true,
                isOverallMetricsScreen = false
            ),
            MetricsState(
                isBugTicketMetricsScreen = false,
                isOverallMetricsScreen = true
            )
        )
        state.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on overall metrics clicked event`() = runTest {
        // GIVEN
        val state = metricsViewModel.state.observe(this)

        // WHEN
        metricsViewModel.onEvent(
            MetricsScreenEvent.OnOverallMetricsButtonClick
        )
        advanceUntilIdle()

        // THEN
        assertThat(state.values).containsExactly(
            MetricsState(
                isBugTicketMetricsScreen = false,
                isOverallMetricsScreen = true
            )
        )
        state.finish()
    }

}