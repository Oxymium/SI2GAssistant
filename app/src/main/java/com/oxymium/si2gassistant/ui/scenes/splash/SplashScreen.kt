package com.oxymium.si2gassistant.ui.scenes.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.scenes.NavigationState
import com.oxymium.si2gassistant.ui.scenes.splash.components.SplashLogo
import com.oxymium.si2gassistant.ui.scenes.splash.components.SplashStartButton
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun SplashScreen(
    navigationEvent: (NavigationEvent) -> Unit
) {
    Surface(
        modifier = Modifier
    ) {
        SplashLogo()
        SplashStartButton(
            navigationEvent = navigationEvent
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    Si2GAssistantTheme {
        SplashScreen() {
            
        }
    }
}