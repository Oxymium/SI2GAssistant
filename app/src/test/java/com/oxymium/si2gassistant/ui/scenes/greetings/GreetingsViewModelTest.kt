package com.oxymium.si2gassistant.ui.scenes.greetings

import com.google.common.truth.Truth
import com.oxymium.si2gassistant.domain.entities.Message
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.AnnouncementRepository
import com.oxymium.si2gassistant.domain.repository.TestRepository
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


class GreetingsViewModelTest {

    private val announcementRepository = mockk<AnnouncementRepository>()
    private val testRepository = mockk<TestRepository>()

    private lateinit var greetingsViewModel: GreetingsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        greetingsViewModel = GreetingsViewModel(announcementRepository, testRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on init ViewModel get all announcements`() = runTest {
        // GIVEN
        val state = greetingsViewModel.state.observe(this)

        val givenAnnouncements = listOf(
            Message("" , 1000L, "", "")
        )
        every { announcementRepository.getAllAnnouncements() } returns flow {
            Result.Success(givenAnnouncements) }

        // WHEN
        greetingsViewModel = GreetingsViewModel(announcementRepository, testRepository)

        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            Message(),
            Message("" , 1000L, "", "")
        )
        state.finish()
    }



}