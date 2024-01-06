package com.oxymium.si2gassistant.ui.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oxymium.si2gassistant.domain.states.AppState
import com.oxymium.si2gassistant.ui.AppEvent
import com.oxymium.si2gassistant.ui.scenes.splash.SplashViewModel
import com.oxymium.si2gassistant.ui.scenes.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginNavGraph(
    navController: NavHostController,
    appState: AppState,
    appEvent: (AppEvent) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = AppRoute.SPLASH_ROUTE.name
    ) {

        navigation(
            route = AppRoute.SPLASH_ROUTE.name,
            startDestination = AppScreen.SPLASH_SCREEN.name
        ) {

            // SCREEN: SPLASH
            composable(
                route = AppScreen.SPLASH_SCREEN.name
            ) {
                val viewModel = koinViewModel<SplashViewModel>()
                val state = viewModel.state.collectAsState()
                SplashScreen(
                    state = state.value,
                    event = viewModel::onEvent,
                    appState = appState,
                    appEvent = appEvent
                )
            }
        }
    }
}