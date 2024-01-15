package com.oxymium.si2gassistant.ui.scenes.reportbug

import com.google.common.truth.Truth
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.BugTicketCategory
import com.oxymium.si2gassistant.domain.entities.BugTicketPriority
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.domain.states.ReportBugState
import com.oxymium.si2gassistant.domain.states.SubmitPersonState
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonEvent
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

class ReportBugViewModelTest {

    private val bugTicketRepository = mockk<BugTicketRepository>()

    private lateinit var reportBugViewModel: ReportBugViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        reportBugViewModel = ReportBugViewModel(bugTicketRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on bug category select event`() = runTest {
        // GIVEN
        val givenCategory = BugTicketCategory.entries.toTypedArray().random()
        val bugTicket = reportBugViewModel.bugTicket.observe(this)
        every { bugTicketRepository.getBugTicketsByUser("") } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        reportBugViewModel.onEvent(
            ReportBugEvent.OnBugCategorySelect(givenCategory)
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(bugTicket.values).containsExactly(
            BugTicket(),
            BugTicket().copy(category = givenCategory)
        )

        bugTicket.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on bug priority select event`() = runTest {
        // GIVEN
        val givenPriority = BugTicketPriority.entries.toTypedArray().random()
        val bugTicket = reportBugViewModel.bugTicket.observe(this)
        every { bugTicketRepository.getBugTicketsByUser("") } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        reportBugViewModel.onEvent(
            ReportBugEvent.OnBugPrioritySelect(givenPriority)
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(bugTicket.values).containsExactly(
            BugTicket(),
            BugTicket().copy(priority = givenPriority)
        )

        bugTicket.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on short description change event`() = runTest {
        // GIVEN
        val givenShortDescription = "short description"
        val bugTicket = reportBugViewModel.bugTicket.observe(this)
        every { bugTicketRepository.getBugTicketsByUser("") } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        reportBugViewModel.onEvent(
            ReportBugEvent.OnShortDescriptionChange(givenShortDescription)
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(bugTicket.values).containsExactly(
            BugTicket(),
            BugTicket().copy(shortDescription = givenShortDescription)
        )

        bugTicket.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on description change event`() = runTest {
        // GIVEN
        val givenDescription = "description"
        val bugTicket = reportBugViewModel.bugTicket.observe(this)
        every { bugTicketRepository.getBugTicketsByUser("") } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        reportBugViewModel.onEvent(
            ReportBugEvent.OnDescriptionChange(givenDescription)
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(bugTicket.values).containsExactly(
            BugTicket(),
            BugTicket().copy(description = givenDescription)
        )

        bugTicket.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on bug tickets mode button click event`() = runTest {
        // GIVEN
        val state = reportBugViewModel.state.observe(this)
        every { bugTicketRepository.getBugTicketsByUser("") } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        reportBugViewModel.onEvent(
            ReportBugEvent.OnBugTicketsModeButtonClick
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            ReportBugState().copy(
                bugTicketsMode = true,
                submitBugTicketMode = false
            )
        )

        state.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on report bug mode button click event`() = runTest {
        // GIVEN
        val state = reportBugViewModel.state.observe(this)
        every { bugTicketRepository.getBugTicketsByUser("") } returns flow { Result.Success(emptyList<List<Person>>()) }

        // WHEN
        reportBugViewModel.onEvent(
            ReportBugEvent.OnReportBugModeButtonClick
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            ReportBugState(),
            ReportBugState().copy(
                bugTicketsMode = false,
                submitBugTicketMode = true
            )
        )

        state.finish()
    }

}