package com.oxymium.si2gassistant.ui.scenes.login.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun LoginMail() {

    var mail by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = mail,
            onValueChange = { mail = it.filter { char -> !char.isWhitespace() }.take(20) },
            label = {
                Text(
                    text = "Mail",
                    color = Color.Black
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Email
            ),
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginMailPreview() {
    Si2GAssistantTheme {
        LoginMail()
    }
}