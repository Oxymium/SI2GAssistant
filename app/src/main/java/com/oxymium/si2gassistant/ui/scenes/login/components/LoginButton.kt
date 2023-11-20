package com.oxymium.si2gassistant.ui.scenes.login.components

import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.domain.usecase.LoginEvent
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun LoginButton(
    navigationEvent: (NavigationEvent) -> Unit,
    event: (LoginEvent) -> Unit
) {
    Button(
        modifier = Modifier,
        onClick = {
            event.invoke(LoginEvent.OnClickLoginButton)
            navigationEvent.invoke(NavigationEvent.OnLoginButtonClicked)
        }
    ) {
        Text(
            text = "LOGIN",
            color = White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginButtonPreview() {
    Si2GAssistantTheme {
    }
}