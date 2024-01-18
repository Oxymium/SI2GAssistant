package com.oxymium.si2gassistant.ui.scenes.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.mock.provideRandomMessage
import com.oxymium.si2gassistant.ui.scenes.animations.NothingAnimation
import com.oxymium.si2gassistant.ui.scenes.chat.components.MessageList
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.TextAccent
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun ChatScreen(
    state: ChatState,
    event: (ChatEvent) -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            // WRITE AREA
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Neutral
                    )
            ) {

                // CONTENT
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Neutral,
                        )
                ) {

                    // CONTENT
                    var content by remember { mutableStateOf("") }

                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = content,
                            onValueChange = {
                                val contentTrimmed = it.trim()
                                if (contentTrimmed.isNotBlank() || it.isEmpty()) {
                                    content = it.take(500)
                                    event.invoke(ChatEvent.OnContentChange(content))
                                }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = White,
                                cursorColor = White,
                                focusedBorderColor = TextAccent,
                                unfocusedBorderColor = White
                            ),
                            label = {
                                Text(
                                    text = if (state.isContentFieldError) "*cannot post empty field" else "Write your message...",
                                    color = White
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Email
                            ),
                            minLines = 3,
                            maxLines = 3,
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        event.invoke(ChatEvent.OnSubmitMessageButtonClick)
                                        content = ""
                                    }) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_plus_thick),
                                        contentDescription = "submit message button",
                                        tint = White
                                    )

                                }
                            }
                        )

                    }
                }
            }

            if (state.messages.isEmpty()) {

                NothingAnimation()

            } else {

                // MESSAGES
                MessageList(
                    state = state,
                    event = event
                )

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    Si2GAssistantTheme {
        val previewState = ChatState(
            messages = List (10) { provideRandomMessage() }
        )
        ChatScreen(
            state = previewState
        ) {

        }
    }
}