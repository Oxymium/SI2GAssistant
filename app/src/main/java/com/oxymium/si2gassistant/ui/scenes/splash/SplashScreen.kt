package com.oxymium.si2gassistant.ui.scenes.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.domain.states.AppState
import com.oxymium.si2gassistant.domain.states.SplashState
import com.oxymium.si2gassistant.ui.AppEvent
import com.oxymium.si2gassistant.ui.scenes.splash.components.LogoScreen
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun SplashScreen(
    state: SplashState,
    appState: AppState,
    event: (SplashEvent) -> Unit,
    appEvent: (AppEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Neutral
            )
    ) {

        if (state.isLogoScreen) {
            LogoScreen()
        }

        if (state.isLoginScreen) {
            LoginScreen(
                state = state,
                event = event,
                appState = appState,
                appEvent = appEvent
            )
        }
    }

}

@Preview
@Composable
fun SplashScreenPreview() {
    Si2GAssistantTheme {
        val previewState = SplashState()
        val appStatePreview = AppState()
        SplashScreen(
            state = previewState,
            appState = appStatePreview,
            {},
            {}
        )
    }
}