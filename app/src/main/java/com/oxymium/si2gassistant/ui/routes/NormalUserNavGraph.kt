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
import com.oxymium.si2gassistant.ui.scenes.greetings.GreetingsScreen
import com.oxymium.si2gassistant.ui.scenes.greetings.GreetingsViewModel
import com.oxymium.si2gassistant.ui.scenes.reportbug.ReportBugScreen
import com.oxymium.si2gassistant.ui.scenes.reportbug.ReportBugViewModel
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonScreen
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonViewModel
import com.oxymium.si2gassistant.ui.scenes.submitsuggestion.SubmitSuggestionScreen
import com.oxymium.si2gassistant.ui.scenes.submitsuggestion.SubmitSuggestionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NormalUserNavGraph(
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
                    navController.navigate(it.name) // handles navigation
                    appEvent.invoke(AppEvent.OnItemMenuButtonClick(it)) // update current route state
                }
            )
        },
        content = {

            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = AppRoute.NORMAL_USER_ROUTE.name
            ) {

                navigation(
                    route = AppRoute.NORMAL_USER_ROUTE.name,
                    startDestination = AppScreen.GREETINGS_SCREEN.name
                ) {

                    // SCREEN: GREETINGS
                    composable(
                        route = AppScreen.GREETINGS_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<GreetingsViewModel>()
                        val state = viewModel.state.collectAsState()
                        GreetingsScreen(
                            state = state.value,
                            appState = appState,
                            appEvent = appEvent
                        )
                    }

                    // SCREEN: SUBMIT PERSON
                    composable(
                        route = AppScreen.SUBMIT_PERSON_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<SubmitPersonViewModel>()
                        val state = viewModel.state.collectAsState()
                        SubmitPersonScreen(
                            state = state.value,
                            event = viewModel::onEvent
                        )
                    }

                    // SCREEN: REPORT BUG TICKET
                    composable(
                        route = AppScreen.REPORT_BUG_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<ReportBugViewModel>()
                        val state = viewModel.state.collectAsState()
                        ReportBugScreen(
                            state = state.value,
                            event = viewModel::onEvent
                        )
                    }

                    // SCREEN: SUBMIT SUGGESTION SCREEN
                    composable(
                        route = AppScreen.SUBMIT_SUGGESTION_SCREEN.name
                    ) {
                        val viewModel = koinViewModel<SubmitSuggestionViewModel>()
                        val state = viewModel.state.collectAsState()
                        SubmitSuggestionScreen(
                            state = state.value,
                            event = viewModel::onEvent
                        )
                    }

                }
            }
        }
    )
}
