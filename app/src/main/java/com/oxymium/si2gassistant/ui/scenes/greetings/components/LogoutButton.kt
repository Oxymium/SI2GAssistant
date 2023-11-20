package com.oxymium.si2gassistant.ui.scenes.greetings.components

import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun LogoutButton() {
    Button(
        onClick = { /*TODO*/ }
    ) {
        Text(text = "LOGOUT")
    }
}

@Preview(showBackground = true)
@Composable
fun LoginButtonPreview() {
    Si2GAssistantTheme {
        LogoutButton()
    }
}