package com.oxymium.si2gassistant.ui.navigation

import com.oxymium.si2gassistant.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

class NavigationViewModelTest {

    private lateinit var navigationViewModel: NavigationViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        navigationViewModel =  NavigationViewModel()
    }

}