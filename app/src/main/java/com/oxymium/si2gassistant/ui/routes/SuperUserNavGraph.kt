package com.oxymium.si2gassistant.ui.routes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oxymium.si2gassistant.ui.NavigationEvent
import com.oxymium.si2gassistant.ui.NavigationState
import com.oxymium.si2gassistant.ui.scenes.bottomnavigationbar.BottomNavigationBar
import com.oxymium.si2gassistant.ui.scenes.buglist.BugTicketViewModel
import com.oxymium.si2gassistant.ui.scenes.buglist.BugTicketsScreen
import com.oxymium.si2gassistant.ui.scenes.greetings.GreetingsScreen
import com.oxymium.si2gassistant.ui.scenes.greetings.GreetingsViewModel
import com.oxymium.si2gassistant.ui.scenes.metrics.MetricsScreen
import com.oxymium.si2gassistant.ui.scenes.metrics.MetricsViewModel
import com.oxymium.si2gassistant.ui.scenes.persons.PersonListViewModel
import com.oxymium.si2gassistant.ui.scenes.persons.PersonsScreen
import com.oxymium.si2gassistant.ui.scenes.suggestions.SuggestionsScreen
import com.oxymium.si2gassistant.ui.scenes.suggestions.SuggestionsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SuperUserNavGraph(
    navController: NavHostController,
    navigationEvent: (NavigationEvent) -> Unit,
    navigationState: NavigationState
) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                navigationState = navigationState,
                onNavigateTo = {
                    navController.navigate(it.name) // handles navigation
                    navigationEvent.invoke(NavigationEvent.OnItemMenuButtonClick(it)) // update current route state
                }
            )
        },
        content = {

            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = AppRoutes.SUPER_USER_ROUTE.name
            ) {

                navigation(
                    route = AppRoutes.SUPER_USER_ROUTE.name,
                    startDestination = AppScreens.GREETINGS_SCREEN.name
                ) {

                    // SCREEN: GREETINGS
                    composable(
                        route = AppScreens.GREETINGS_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<GreetingsViewModel>()
                        val state = viewModel.state.collectAsState()
                        GreetingsScreen(
                            state.value,
                            navigationEvent = navigationEvent
                        )
                    }

                    // SCREEN: METRICS
                    composable(
                        route = AppScreens.METRICS_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<MetricsViewModel>()
                        val state = viewModel.state.collectAsState()
                        MetricsScreen(
                            state.value,
                            viewModel::onEvent
                        )
                    }

                    // SCREEN: ACADEMIES
                    composable(
                        route = AppScreens.PERSONS_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<PersonListViewModel>()
                        val state = viewModel.state.collectAsState()
                        PersonsScreen(
                            state = state.value,
                            event = viewModel::onEvent
                        )
                    }

                    // SCREEN: BUG TICKETS
                    composable(
                        route = AppScreens.BUG_TICKETS_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<BugTicketViewModel>()
                        val state = viewModel.state.collectAsState()
                        BugTicketsScreen(
                            state = state.value,
                            event = viewModel::onEvent
                        )
                    }

                    // SCREEN: SUGGESTIONS
                    composable(
                        route = AppScreens.SUGGESTIONS_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<SuggestionsViewModel>()
                        val state = viewModel.state.collectAsState()
                        SuggestionsScreen(
                            state = state.value,
                            event = viewModel::onEvent
                        )
                    }

                }
            }
        }
    )
}