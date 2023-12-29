package com.oxymium.si2gassistant.ui.scenes.greetings.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun LogoutButton(
    navigationEvent: (NavigationEvent) -> Unit
) {
    Button(
        modifier = Modifier
            .wrapContentSize(),
        onClick = { navigationEvent.invoke(NavigationEvent.OnLogoutButtonClick) }
    ) {
        Text(text = "LOGOUT")
    }
}

@Preview(showBackground = true)
@Composable
fun LoginButtonPreview() {
    Si2GAssistantTheme {
        LogoutButton(){}
    }
}