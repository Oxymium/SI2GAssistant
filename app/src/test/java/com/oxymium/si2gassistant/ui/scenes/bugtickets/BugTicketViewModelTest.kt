package com.oxymium.si2gassistant.ui.scenes.bugtickets

import com.google.common.truth.Truth
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.domain.states.BugTicketsState
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

class BugTicketViewModelTest {

    private val bugTicketRepository = mockk<BugTicketRepository>()

    private lateinit var bugTicketViewModel: BugTicketsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        bugTicketViewModel = BugTicketsViewModel(bugTicketRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `On select bug ticket event test`() = runTest {
        // GIVEN
        val givenBugTicket = provideRandomBugTicket()
        val state = bugTicketViewModel.state.observe(this)
        every { bugTicketRepository.getAllBugTickets() } returns flow { Result.Success(emptyList<List<BugTicket>>()) }

        // WHEN
        bugTicketViewModel.onEvent(
            BugTicketsEvent.SelectBugTicket(givenBugTicket)
        )

        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            BugTicketsState(),
            BugTicketsState(isSelectedBugTicketDetailsOpen = true)
        )

        state.finish()

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `On dismiss bug ticket details event test`() = runTest {
        // GIVEN
        val state = bugTicketViewModel.state.observe(this)
        every { bugTicketRepository.getAllBugTickets() } returns flow { Result.Success(emptyList<List<BugTicket>>()) }

        // WHEN
        bugTicketViewModel.onEvent(
            BugTicketsEvent.DismissBugTicketDetailsSheet
        )

        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            BugTicketsState(selectedBugTicket = null)
        )

        state.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `On resolved comment change event test`() = runTest {
        // GIVEN
        val givenComment = "This is a test comment"
        val state = bugTicketViewModel.state.observe(this)
        every { bugTicketRepository.getAllBugTickets() } returns flow { Result.Success(emptyList<List<BugTicket>>()) }

        // WHEN
        bugTicketViewModel.onEvent(
            BugTicketsEvent.OnResolvedCommentChange(givenComment)
        )

        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            BugTicketsState(),
            BugTicketsState(resolvedComment = givenComment)
        )

        state.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `On resolved details sheet button click event test`() = runTest {
        // GIVEN
        val givenBugTicket = provideRandomBugTicket()
        val state = bugTicketViewModel.state.observe(this)
        every { bugTicketRepository.getAllBugTickets() } returns flow { Result.Success(emptyList<List<BugTicket>>()) }

        // WHEN
        bugTicketViewModel.onEvent(
            BugTicketsEvent.OnResolvedDetailsSheetButtonClick(givenBugTicket)
        )

        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            BugTicketsState(),
            BugTicketsState(isResolvedCommentFieldError = true)
        )

        state.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `On search text change event test`() = runTest {
        // GIVEN
        val givenSearch = "Grenoble"
        val state = bugTicketViewModel.state.observe(this)
        every { bugTicketRepository.getAllBugTickets() } returns flow { Result.Success(emptyList<List<BugTicket>>()) }

        // WHEN
        bugTicketViewModel.onEvent(
            BugTicketsEvent.OnSearchTextChange(givenSearch)
        )

        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            BugTicketsState()
        )

        state.finish()

    }

}