package com.oxymium.si2gassistant.ui

import com.google.common.truth.Truth
import com.oxymium.si2gassistant.domain.repository.AuthRepository
import com.oxymium.si2gassistant.domain.repository.DataStoreRepository
import com.oxymium.si2gassistant.domain.repository.UserRepository
import com.oxymium.si2gassistant.domain.states.AppState
import com.oxymium.si2gassistant.ui.routes.AppScreen
import com.oxymium.si2gassistant.utils.TestCoroutineRule
import com.oxymium.si2gassistant.utils.observe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppViewModelTest {

    private val authRepository = mockk<AuthRepository>()
    private val userRepository = mockk<UserRepository>()
    private val dataStoreRepository = mockk<DataStoreRepository>()


    private lateinit var appViewModel: AppViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        appViewModel = AppViewModel(authRepository, userRepository, dataStoreRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on logout button click event`() = runTest {
        // GIVEN
        val state = appViewModel.state.observe(this)
        coEvery { dataStoreRepository.getString("") } returns String()

        // WHEN
        appViewModel.onEvent(
            AppEvent.OnLogoutButtonClick
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            AppState(),
            AppState(
                currentUser = null
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on item menu button click event`() = runTest {
        // GIVEN
        val givenAppScreen = AppScreen.entries.random()
        val state = appViewModel.state.observe(this)
        coEvery { dataStoreRepository.getString("") } returns ""

        // WHEN
        appViewModel.onEvent(
            AppEvent.OnItemMenuButtonClick(givenAppScreen)
        )

        // THEN
        Truth.assertThat(state.values).containsExactly(
            AppState(),
            AppState(
                currentScreen = givenAppScreen
            )
        )

    }

}