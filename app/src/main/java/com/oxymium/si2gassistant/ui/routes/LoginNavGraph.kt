package com.oxymium.si2gassistant.ui.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oxymium.si2gassistant.ui.scenes.AppRoutes
import com.oxymium.si2gassistant.ui.scenes.AppScreens
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.scenes.splash.LoginScreen
import com.oxymium.si2gassistant.ui.scenes.splash.LoginViewModel
import com.oxymium.si2gassistant.ui.scenes.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginNavGraph(
    navController: NavHostController,
    navigationEvent: (NavigationEvent) -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH_ROUTE.name
    ) {

        navigation(
            route = AppRoutes.SPLASH_ROUTE.name,
            startDestination = AppScreens.SPLASH_SCREEN.name
        ) {

            // SCREEN: SPLASH
            composable(
                route = AppScreens.SPLASH_SCREEN.name
            ) {
                val viewModel = koinViewModel<LoginViewModel>()
                val state = viewModel.state.collectAsState()
                SplashScreen(
                    state = state.value,
                    navigationEvent = navigationEvent,
                    event = viewModel::onEvent
                )
            }
        }
    }
}