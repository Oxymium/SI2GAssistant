package com.oxymium.si2gassistant.ui.scenes.persons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.entities.mock.ALL_MODULES
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonEvent
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonState
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.NeutralLighter
import com.oxymium.si2gassistant.ui.theme.NotValidatedModule
import com.oxymium.si2gassistant.ui.theme.ValidatedModule
import com.oxymium.si2gassistant.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonBottomSheet(
    state: SubmitPersonState,
    event: (SubmitPersonEvent) -> Unit
) {

    if (state.selectedPerson != null) {

        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(0.95f),
            containerColor = Neutral,
            onDismissRequest = { event.invoke(SubmitPersonEvent.DismissPersonDetailsSheet) }
        ) {

            // PRE INFO
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = NeutralLighter,
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {

                    // Firstname
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "firstname: ${state.selectedPerson.firstname}",
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    // Lastname
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "lastname: ${state.selectedPerson.lastname}",
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    // Role
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "academy: ${state.selectedPerson.role}",
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    // Academy
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "academy: ${state.selectedPerson.academy}",
                        color = White,
                        textAlign = TextAlign.Center
                    )

                }

            }

            // SPACER
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.ic_blur),
                tint = NeutralLighter,
                contentDescription = "circle"
            )

            // USER INFO
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = NeutralLighter,
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {

                    // Firstname
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "user firstname: ${state.selectedPerson.userFirstname}",
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    // Lastname
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "user lastname: ${state.selectedPerson.userLastname}",
                        color = White,
                        textAlign = TextAlign.Center
                    )

                }

            }

            // SPACER
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.ic_blur),
                tint = NeutralLighter,
                contentDescription = "circle"
            )

            // MODULES
            val validatedModules: MutableState<List<Int>> = remember { mutableStateOf(state.selectedPerson.validatedModules?.split(", ")?.map { it.toInt() } ?: emptyList()) }

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = NeutralLighter,
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {

                    for (i in ALL_MODULES) {

                        // MODULES
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                            ) {

                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically),
                                    text = i.id.toString(),
                                    color = Color.White
                                )

                                Switch(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.CenterVertically),
                                    colors = SwitchDefaults.colors(
                                        uncheckedThumbColor = NotValidatedModule,
                                        checkedThumbColor = ValidatedModule
                                    ),
                                    checked = i.id?.toInt() in validatedModules.value,
                                    onCheckedChange = { isChecked ->

                                        val moduleId = i.id?.toInt() ?: return@Switch

                                        // Update the validatedModules list based on the switch state
                                        validatedModules.value = if (isChecked) {
                                            validatedModules.value + moduleId
                                        } else {
                                            validatedModules.value - moduleId
                                        }

                                    }
                                )

                            }
                        }
                    }

                }

            }


            // UPDATE PERSON BUTTON
            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Neutral
                ),
                onClick = {
                    val modulesAsString = validatedModules.value.joinToString(", ") // turn the list back into a String separated by commas
                    event.invoke(SubmitPersonEvent.OnPersonModulesUpdateButtonClicked(modulesAsString))
                }
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