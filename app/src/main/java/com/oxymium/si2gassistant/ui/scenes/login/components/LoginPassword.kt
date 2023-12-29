package com.oxymium.si2gassistant.ui.scenes.login.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.domain.usecase.LoginEvent
import com.oxymium.si2gassistant.domain.usecase.LoginState
import com.oxymium.si2gassistant.ui.theme.Orange500
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun LoginPassword(
    state: LoginState,
    event: (LoginEvent) -> Unit
) {

    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it.filter { char -> !char.isWhitespace() }.take(20)
                event.invoke(LoginEvent.OnLoginPasswordChanged(password))
                            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Orange500,
                cursorColor = White,
                focusedBorderColor = Orange500,
                unfocusedBorderColor = White,
            ),
            label = {
                Text(
                    text = if (state.isPasswordFieldError) "Password input error" else "Password",
                    color = White
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPasswordPreview() {
    Si2GAssistantTheme {
        val previewState = LoginState()
        LoginPassword(
            state = previewState
        ) { }
    }
}