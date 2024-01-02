package com.oxymium.si2gassistant.ui.scenes.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.scenes.splash.components.LogoScreen
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun SplashScreen(
    state: SplashState,
    event: (SplashEvent) -> Unit,
    navigationEvent: (NavigationEvent) -> Unit
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
                navigationEvent = navigationEvent,
            )
        }
    }

}

@Preview
@Composable
fun SplashScreenPreview() {
    Si2GAssistantTheme {
        val previewState = SplashState()
        SplashScreen(
            state = previewState,
            {},
            {}
        )
    }
}