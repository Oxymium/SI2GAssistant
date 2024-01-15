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
import com.oxymium.si2gassistant.domain.states.AppState
import com.oxymium.si2gassistant.ui.AppEvent
import com.oxymium.si2gassistant.ui.scenes.bottomnavigationbar.BottomNavigationBar
import com.oxymium.si2gassistant.ui.scenes.bugtickets.BugTicketsViewModel
import com.oxymium.si2gassistant.ui.scenes.bugtickets.BugTicketsScreen
import com.oxymium.si2gassistant.ui.scenes.chat.ChatScreen
import com.oxymium.si2gassistant.ui.scenes.chat.ChatViewModel
import com.oxymium.si2gassistant.ui.scenes.greetings.GreetingsScreen
import com.oxymium.si2gassistant.ui.scenes.greetings.GreetingsViewModel
import com.oxymium.si2gassistant.ui.scenes.metrics.MetricsScreen
import com.oxymium.si2gassistant.ui.scenes.metrics.MetricsViewModel
import com.oxymium.si2gassistant.ui.scenes.persons.PersonsViewModel
import com.oxymium.si2gassistant.ui.scenes.persons.PersonsScreen
import com.oxymium.si2gassistant.ui.scenes.suggestions.SuggestionsScreen
import com.oxymium.si2gassistant.ui.scenes.suggestions.SuggestionsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SuperUserNavGraph(
    navController: NavHostController,
    appState: AppState,
    appEvent: (AppEvent) -> Unit,
) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                appState = appState,
                onNavigateTo = {
                    navController.navigate(it.name) {
                        popUpTo(AppRoute.SUPER_USER_ROUTE.name) {
                            inclusive = true
                        }
                    }
                    appEvent.invoke(AppEvent.OnItemMenuButtonClick(it)) // update current route state
                }
            )
        },
        content = {

            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = AppRoute.SUPER_USER_ROUTE.name
            ) {

                navigation(
                    route = AppRoute.SUPER_USER_ROUTE.name,
                    startDestination = AppScreen.GREETINGS_SCREEN.name
                ) {

                    // SCREEN: GREETINGS
                    composable(
                        route = AppScreen.GREETINGS_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<GreetingsViewModel>()
                        val state = viewModel.state.collectAsState()
                        GreetingsScreen(
                            state.value,
                            appState = appState,
                            appEvent = appEvent
                        )
                    }

                    // SCREEN: METRICS
                    composable(
                        route = AppScreen.METRICS_SCREEN.name
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
                        route = AppScreen.PERSONS_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<PersonsViewModel>()
                        val state = viewModel.state.collectAsState()
                        PersonsScreen(
                            state = state.value,
                            event = viewModel::onEvent
                        )
                    }

                    // SCREEN: BUG TICKETS
                    composable(
                        route = AppScreen.BUG_TICKETS_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<BugTicketsViewModel>()
                        val state = viewModel.state.collectAsState()
                        BugTicketsScreen(
                            state = state.value,
                            event = viewModel::onEvent
                        )
                    }

                    // SCREEN: SUGGESTIONS
                    composable(
                        route = AppScreen.SUGGESTIONS_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<SuggestionsViewModel>()
                        val state = viewModel.state.collectAsState()
                        SuggestionsScreen(
                            state = state.value,
                            event = viewModel::onEvent
                        )
                    }

                    // SCREEN: CHAT
                    composable(
                        route = AppScreen.CHAT_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<ChatViewModel>()
                        val state = viewModel.state.collectAsState()
                        ChatScreen(
                            state = state.value,
                            event = viewModel::onEvent
                        )

                    }


                }
            }
        }
    )
}