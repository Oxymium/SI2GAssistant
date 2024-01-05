package com.oxymium.si2gassistant.ui.scenes.reportbug

import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.utils.TestCoroutineRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

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
}