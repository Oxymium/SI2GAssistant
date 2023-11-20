package com.oxymium.si2gassistant.ui.scenes.greetings.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun GreetingsMessage() {
    Text(
        text = "Welcome, User",
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingsMessagePreview() {
    Si2GAssistantTheme {
        GreetingsMessage()
    }
}