package com.oxymium.si2gassistant.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.oxymium.si2gassistant.domain.entities.User
import com.oxymium.si2gassistant.ui.routes.LoginNavGraph
import com.oxymium.si2gassistant.ui.routes.NormalUserNavGraph
import com.oxymium.si2gassistant.ui.routes.SuperUserNavGraph
import com.oxymium.si2gassistant.ui.scenes.AppScreens
import org.koin.androidx.compose.koinViewModel

val LocalUserContext = compositionLocalOf<User?> { User() }

@Composable
fun App(navController: NavHostController) {

    val navigationViewModel = koinViewModel<NavigationViewModel>()
    val navigationState = navigationViewModel.state.collectAsState()

    CompositionLocalProvider(
        LocalUserContext provides navigationState.value.currentUser
    ) {

        when (navigationState.value.currentUser?.hasAdministrativeRights) {

            // LOGIN BLOCK
            null -> LoginNavGraph(
                navController = navController,
                navigationEvent = navigationViewModel::onEvent
            )

            // NORMAL USER BLOCK
            false -> NormalUserNavGraph(
                navController = navController,
                navigationEvent = navigationViewModel::onEvent,
                navigationState = navigationState.value
            )

            // SUPER USER BLOCK
            true -> SuperUserNavGraph(
                navController = navController,
                navigationEvent = navigationViewModel::onEvent,
                navigationState = navigationState.value
            )
        }

    }

    when (navigationState.value.navigationScreen) {

        // GREETINGS SCREEN
        AppScreens.GREETINGS_SCREEN.name -> navController.navigate(AppScreens.GREETINGS_SCREEN.name)
        // SPLASH SCREEN
        AppScreens.SPLASH_SCREEN.name -> navController.navigate(AppScreens.SPLASH_SCREEN.name)
        // METRICS SCREEN
        AppScreens.METRICS_SCREEN.name -> navController.navigate(AppScreens.METRICS_SCREEN.name)
        // PERSONS SCREEN
        AppScreens.PERSONS_SCREEN.name -> navController.navigate(AppScreens.PERSONS_SCREEN.name)
        // BUG TICKETS SCREEN
        AppScreens.BUG_TICKETS_SCREEN.name -> navController.navigate(AppScreens.BUG_TICKETS_SCREEN.name)
        // REPORT BUG TICKET SCREEN
        AppScreens.REPORT_BUG_SCREEN.name -> navController.navigate(AppScreens.REPORT_BUG_SCREEN.name)
        // SUBMIT PERSON SCREEN
        AppScreens.SUBMIT_PERSON_SCREEN.name -> navController.navigate(AppScreens.SUBMIT_PERSON_SCREEN.name)
        // SUBMIT SUGGESTION SCREEN
        AppScreens.SUBMIT_SUGGESTION_SCREEN.name -> navController.navigate(AppScreens.SUBMIT_SUGGESTION_SCREEN.name)
        // SUGGESTIONS SCREEN
        AppScreens.SUGGESTIONS_SCREEN.name -> navController.navigate(AppScreens.SUGGESTIONS_SCREEN.name)

    }

}
