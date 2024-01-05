package com.oxymium.si2gassistant.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.oxymium.si2gassistant.domain.entities.User
import com.oxymium.si2gassistant.ui.routes.LoginNavGraph
import com.oxymium.si2gassistant.ui.routes.NormalUserNavGraph
import com.oxymium.si2gassistant.ui.routes.SuperUserNavGraph
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

}
