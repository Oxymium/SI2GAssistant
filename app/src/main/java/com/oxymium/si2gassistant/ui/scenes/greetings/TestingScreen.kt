package com.oxymium.si2gassistant.ui.scenes.greetings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.TextAccent
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun TestingScreen(
    event: (GreetingsEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Neutral
            )
    ) {

        // HIDDEN FOR DEBUG PURPOSES
        // TESTING BUTTON
        Box(
            modifier = Modifier
        ) {

            Button(
                modifier = Modifier
                    .align(Alignment.Center),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Neutral
                ),
                onClick = { event.invoke(GreetingsEvent.OnGreetingsButtonClick) }
            ) {

                androidx.compose.material3.Icon(
                    modifier = Modifier
                        .background(Neutral)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_account),
                    contentDescription = "Logout button",
                    tint = White
                )
            }

        }

        // ACADEMIES
        val academies by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = academies,
                onValueChange = {},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = White,
                    cursorColor = White,
                    focusedBorderColor = TextAccent,
                    unfocusedBorderColor = White
                ),
                label = {
                    Text(
                        text = "Load Academies",
                        color = White
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                minLines = 1,
                maxLines = 1,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            event.invoke(GreetingsEvent.OnAcademyClick)
                        }
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus_thick),
                            contentDescription = "academies button",
                            tint = White
                        )

                    }
                }
            )

        }

        // MODULES
        val modules by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = modules,
                onValueChange = {},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = White,
                    cursorColor = White,
                    focusedBorderColor = TextAccent,
                    unfocusedBorderColor = White
                ),
                label = {
                    Text(
                        text = "Load Modules",
                        color = White
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                minLines = 1,
                maxLines = 1,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            event.invoke(GreetingsEvent.OnModuleClick)
                        }
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus_thick),
                            contentDescription = "academies button",
                            tint = White
                        )

                    }
                }
            )

        }

        // ANNOUNCEMENTS
        val announcements by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = announcements,
                onValueChange = {},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = White,
                    cursorColor = White,
                    focusedBorderColor = TextAccent,
                    unfocusedBorderColor = White
                ),
                label = {
                    Text(
                        text = "Load Announcements",
                        color = White
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                minLines = 1,
                maxLines = 1,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            event.invoke(GreetingsEvent.OnAnnouncementClick)
                        }
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus_thick),
                            contentDescription = "academies button",
                            tint = White
                        )

                    }
                }
            )

        }


        // BUG TICKETS
        var bugTickets by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = bugTickets,
                onValueChange = {
                    val contentTrimmed = it.trim()
                    if (contentTrimmed.isNotBlank() || it.isEmpty()) {
                        bugTickets = it.take(2)
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
                        text = "Bug tickets quantity",
                        color = White
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                minLines = 1,
                maxLines = 1,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            event.invoke(GreetingsEvent.OnBugTicketClick(bugTickets.toInt()))
                            bugTickets = ""
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

        // PERSONS
        var persons by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = persons,
                onValueChange = {
                    val contentTrimmed = it.trim()
                    if (contentTrimmed.isNotBlank() || it.isEmpty()) {
                        persons = it.take(2)
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
                        text = "Persons quantity",
                        color = White
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                minLines = 1,
                maxLines = 1,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            event.invoke(GreetingsEvent.OnPersonQuantityClick(persons.toInt()))
                            persons = ""
                        }) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus_thick),
                            contentDescription = "persons buttons",
                            tint = White
                        )

                    }
                }
            )

        }

        // MESSAGES
        var messages by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = messages,
                onValueChange = {
                    val contentTrimmed = it.trim()
                    if (contentTrimmed.isNotBlank() || it.isEmpty()) {
                        messages = it.take(2)
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
                        text = "Messages quantity",
                        color = White
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                minLines = 1,
                maxLines = 1,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            event.invoke(GreetingsEvent.OnMessageQuantityClick(messages.toInt()))
                            messages = ""
                        }) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus_thick),
                            contentDescription = "messages buttons",
                            tint = White
                        )

                    }
                }
            )

        }

        // SUGGESTIONS
        var suggestions by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = suggestions,
                onValueChange = {
                    val contentTrimmed = it.trim()
                    if (contentTrimmed.isNotBlank() || it.isEmpty()) {
                        suggestions = it.take(2)
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
                        text = "Suggestions quantity",
                        color = White
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                minLines = 1,
                maxLines = 1,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            event.invoke(GreetingsEvent.OnSuggestionQuantityClick(suggestions.toInt()))
                            suggestions = ""
                        }) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus_thick),
                            contentDescription = "messages buttons",
                            tint = White
                        )

                    }
                }
            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun TestingScreenTest() {
    Si2GAssistantTheme {
        TestingScreen(
            event = {}
        )
    }
}