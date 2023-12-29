package com.oxymium.si2gassistant.ui.scenes.submitsuggestion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Orange500
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun SubmitSuggestionScreen(
    state: SubmitSuggestionState,
    event: (SubmitSuggestionEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // SUBJECT
        var subject by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = subject,
                onValueChange = {
                    subject = it.take(40)
                    event.invoke(SubmitSuggestionEvent.OnSuggestionSubjectChanged(subject))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Orange500,
                    cursorColor = Color.Black,
                    focusedBorderColor = Orange500,
                    unfocusedBorderColor = Color.Black
                ),
                label = {
                    Text(
                        text = "Subject",
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

        if (state.isSubjectFieldError) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End),
                text = "*subject cannot be empty",
                color = Color.Red
            )
        }

        // BODY
        var body by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = body,
                onValueChange = {
                    body = it.take(500)
                    event.invoke(SubmitSuggestionEvent.OnSuggestionBodyChanged(body))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Orange500,
                    cursorColor = Color.Black,
                    focusedBorderColor = Orange500,
                    unfocusedBorderColor = Color.Black
                ),
                label = {
                    Text(
                        text = "Body",
                        color = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                minLines = 15,
                maxLines = 15
            )
        }

        if (state.isBodyFieldError) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End),
                text = "*body cannot be empty",
                color = Color.Red
            )
        }

        // PUSH SUGGESTION BUTTON
        Button(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Neutral
            ),
            onClick = { event.invoke(SubmitSuggestionEvent.OnSubmitSuggestionButtonClicked) }
        ) {

            Icon(
                modifier = Modifier
                    .background(Neutral)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_cloud_upload),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SubmitSuggestionScreenPreview() {
    Si2GAssistantTheme {
        val previewState = SubmitSuggestionState(
            isSubjectFieldError = true,
            isBodyFieldError = true
        )
        SubmitSuggestionScreen(
            state = previewState
        ) {}
    }
}