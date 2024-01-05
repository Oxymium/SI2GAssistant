package com.oxymium.si2gassistant.ui.scenes.submitperson.components

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
import com.oxymium.si2gassistant.ui.scenes.animations.UploadingAnimation
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonEvent
import com.oxymium.si2gassistant.domain.states.SubmitPersonState
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.TextAccent

@Composable
fun SubmitPersonScreenTest(
    state: SubmitPersonState,
    event: (SubmitPersonEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxSize()
    ) {

        // ROLE
        var role by remember { mutableStateOf("") }

        // ROLE
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = role,
            onValueChange = {
                role = it.take(50)
                event.invoke(SubmitPersonEvent.OnPersonRoleChange(role))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = TextAccent,
                cursorColor = Color.Black,
                focusedBorderColor = TextAccent,
                unfocusedBorderColor = Color.Black
            ),
            label = {
                Text(
                    text = "Role",
                    color = Color.Black
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Email
            ),
            maxLines = 1
        )

        if (state.isRoleFieldError) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End),
                text = "*role cannot be empty",
                color = Color.Red
            )
        }

        // FIRST NAME
        var firstname by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = firstname,
                onValueChange = {
                    firstname = it.take(50)
                    event.invoke(SubmitPersonEvent.OnPersonFirstNameChange(firstname))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = TextAccent,
                    cursorColor = Color.Black,
                    focusedBorderColor = TextAccent,
                    unfocusedBorderColor = Color.Black
                ),
                label = {
                    Text(
                        text = "First name",
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

        if (state.isFirstnameFieldError) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End),
                text = "*firstname cannot be empty",
                color = Color.Red
            )
        }

        // LAST NAME
        var lastname by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = lastname,
                onValueChange = {
                    lastname = it.take(50)
                    event.invoke(SubmitPersonEvent.OnPersonLastNameChange(lastname))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = TextAccent,
                    cursorColor = Color.Black,
                    focusedBorderColor = TextAccent,
                    unfocusedBorderColor = Color.Black
                ),
                label = {
                    Text(
                        text = "Last name",
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

        if (state.isLastnameFieldError) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End),
                text = "*lastname cannot be empty",
                color = Color.Red
            )
        }
        
        if (state.isPersonSubmitLoading) {

            UploadingAnimation()

        } else {

            // PUSH PERSON BUTTON
            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Neutral
                ),
                onClick = { event.invoke(SubmitPersonEvent.OnSubmitPersonButtonClick) }
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

}

@Preview(showBackground = true)
@Composable
fun PersonsScreenTestPreview() {
    Si2GAssistantTheme {
        val previewState = SubmitPersonState(
            isRoleFieldError = false,
            isFirstnameFieldError = false,
            isLastnameFieldError = false
        )
        SubmitPersonScreenTest(
            state = previewState
        ) {
        }
    }
}