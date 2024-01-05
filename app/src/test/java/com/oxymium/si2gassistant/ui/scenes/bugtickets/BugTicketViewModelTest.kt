package com.oxymium.si2gassistant.ui.scenes.bugtickets

import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.utils.TestCoroutineRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

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

}