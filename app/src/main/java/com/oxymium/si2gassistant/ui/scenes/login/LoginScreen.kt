package com.oxymium.si2gassistant.ui.scenes.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.domain.entities.Auth
import com.oxymium.si2gassistant.domain.usecase.LoginEvent
import com.oxymium.si2gassistant.domain.usecase.LoginState
import com.oxymium.si2gassistant.ui.NavigationViewModel
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.scenes.login.components.LoginButton
import com.oxymium.si2gassistant.ui.scenes.login.components.LoginMail
import com.oxymium.si2gassistant.ui.scenes.login.components.LoginPassword
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    state: LoginState,
    navigationEvent: (NavigationEvent) -> Unit,
    event: (LoginEvent) -> Unit
) {

    Column {
        LoginMail()
        LoginPassword()
        LoginButton(
            navigationEvent = navigationEvent,
            event = event
        )

        if (!state.auth?.displayedName.isNullOrEmpty()) {
            Text(text = state.auth?.displayedName.toString())
        }
        Text(text = state.auth?.displayedName ?: "Fail")

        if (state.isLoading) {
            Text(text = "isLoading")
        }

        if (state.isSuccessful) {
            Text(text = "successful")
        }

        if (state.loginError?.isError == true) {
            Text(text = "something went wrong")
            Text(text = "${state.loginError.errorMessage}")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val loginPreview = Auth("mail@mock.test", "Random")
    val state = LoginState(auth = loginPreview)
    Si2GAssistantTheme {
    }
}