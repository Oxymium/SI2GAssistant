package com.oxymium.si2gassistant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oxymium.si2gassistant.ui.scenes.AppRoutes
import com.oxymium.si2gassistant.ui.scenes.AppScreens
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.scenes.NavigationState
import com.oxymium.si2gassistant.ui.scenes.buglist.BugTicketViewModel
import com.oxymium.si2gassistant.ui.scenes.buglist.BugTicketsScreen
import com.oxymium.si2gassistant.ui.scenes.greetings.GreetingsScreen
import com.oxymium.si2gassistant.ui.scenes.login.LoginScreen
import com.oxymium.si2gassistant.ui.scenes.login.LoginViewModel
import com.oxymium.si2gassistant.ui.scenes.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavHostController(navHostController: NavHostController) {

    val navigationViewModel = koinViewModel<NavigationViewModel>()
    val navigationState = navigationViewModel.state.collectAsState(initial = null)
    // Handle navigation
    when (navigationState.value?.navigationRoute) {
        // SPLASH -> LOGIN
        AppScreens.LOGIN_SCREEN.name -> {
            navHostController.navigate(AppScreens.LOGIN_SCREEN.name)
            navigationViewModel.updateState(NavigationState((null)))
        }
        // LOGIN -> SUPER USER
        AppRoutes.SUPER_USER_ROUTE.name -> {
            navHostController.navigate(AppRoutes.SUPER_USER_ROUTE.name)
            navigationViewModel.updateState(NavigationState((null)))
        }
        // GREETINGS
        AppScreens.GREETINGS_SCREEN.name -> {
            navHostController.navigate(AppScreens.GREETINGS_SCREEN.name)
            navigationViewModel.updateState(NavigationState((null)))
        }
        // BUG TICKETS
        AppScreens.BUG_TICKETS_SCREEN.name -> {
            navHostController.navigate(AppScreens.BUG_TICKETS_SCREEN.name)
            navigationViewModel.updateState(NavigationState((null)))
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = AppRoutes.LOGIN_ROUTE.name
    ) {
        // -----------
        // LOGIN ROUTE
        // -----------
        navigation(
            route = AppRoutes.LOGIN_ROUTE.name,
            startDestination = AppScreens.SPLASH_SCREEN.name
        ) {
            // SPLASH SCREEN
            composable(
                route = AppScreens.SPLASH_SCREEN.name
            ) {
                SplashScreen(
                    navigationViewModel::onEvent
                )
            }
            // LOGIN SCREEN
            composable(
                route = AppScreens.LOGIN_SCREEN.name
            ) {
                val viewModel = koinViewModel<LoginViewModel>()
                val state = viewModel.state.collectAsState()
                LoginScreen(
                    state.value,
                    navigationViewModel::onEvent,
                    viewModel::onEvent
                )
            }
        }

        // ---------------
        // GREETINGS ROUTE
        // ---------------
        navigation(
            route = AppRoutes.GREETINGS_ROUTE.name,
            startDestination = AppScreens.GREETINGS_SCREEN.name
        ) {

            // BUG TICKETS SCREEN
            composable(
                route = AppScreens.GREETINGS_SCREEN.name
            ) {
                GreetingsScreen()
            }
        }

        // ----------------
        // BUG TICKET ROUTE
        // ----------------
        navigation(
            route = AppRoutes.BUG_TICKETS_ROUTE.name,
            startDestination = AppScreens.BUG_TICKETS_SCREEN.name
        ) {

            // BUG TICKETS SCREEN
            composable(
                route = AppScreens.BUG_TICKETS_SCREEN.name
            ) {
                BugTicketsScreen()
            }
        }

    }
}

@Composable
fun Scaffold(
    navHostController: NavHostController,
    navigationEvent: (NavigationEvent) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navHostController,
                navigationEvent
            )
        },
        content = {
            GreetingsScreen(modifier = Modifier.padding(it))
        }
    )
}