package com.oxymium.si2gassistant.ui.scenes.greetings

import com.oxymium.si2gassistant.domain.repository.AnnouncementRepository
import com.oxymium.si2gassistant.utils.TestCoroutineRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule


class GreetingsViewModelTest {

    private val announcementRepository = mockk<AnnouncementRepository>()

    private lateinit var greetingsViewModel: GreetingsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        greetingsViewModel = GreetingsViewModel(announcementRepository)
    }


}