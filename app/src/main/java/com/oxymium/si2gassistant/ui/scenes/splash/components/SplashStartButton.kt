package com.oxymium.si2gassistant.ui.scenes.splash.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun SplashStartButton(
    navigationEvent: (NavigationEvent) -> Unit
) {
    Button(
        onClick = { navigationEvent.invoke(NavigationEvent.OnSplashButtonClicked) }
        ) {
        Icon(
            modifier = Modifier
                .size(24.dp),
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashStartButtonPreview() {
    Si2GAssistantTheme {
        SplashStartButton() {

        }
    }
}