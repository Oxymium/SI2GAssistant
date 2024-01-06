package com.oxymium.si2gassistant.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.oxymium.si2gassistant.ui.AppViewModel
import com.oxymium.si2gassistant.ui.routes.LoginNavGraph
import com.oxymium.si2gassistant.ui.routes.NormalUserNavGraph
import com.oxymium.si2gassistant.ui.routes.SuperUserNavGraph
import org.koin.androidx.compose.koinViewModel

@Composable
fun App(navController: NavHostController) {

    val appViewModel = koinViewModel<AppViewModel>()
    val appState = appViewModel.state.collectAsState()

    when (appState.value.currentUser?.hasAdministrativeRights) {

        // LOGIN BLOCK
        null -> LoginNavGraph(
            navController = navController,
            appState = appState.value,
            appEvent = appViewModel::onEvent
        )

        // NORMAL USER BLOCK
        false -> NormalUserNavGraph(
            navController = navController,
            appState = appState.value,
            appEvent = appViewModel::onEvent,
        )

        // SUPER USER BLOCK
        true -> SuperUserNavGraph(
            navController = navController,
            appState = appState.value,
            appEvent = appViewModel::onEvent,
        )
    }

}

