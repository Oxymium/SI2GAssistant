package com.oxymium.si2gassistant.ui.scenes.submitperson.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.mock.ALL_MODULES
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonEvent
import com.oxymium.si2gassistant.domain.states.SubmitPersonState
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
                        color = NeutralLighter
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
                        text = "${state.selectedPerson.firstname}",
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    // Lastname
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "${state.selectedPerson.lastname}",
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    // Role
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "${state.selectedPerson.role}",
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

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = NeutralLighter
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {

                    for (i in ALL_MODULES) {

                        val moduleId = i.id?.toInt()
                        // Check if MODULE ID is in the validatedModules String
                        val isChecked = state.selectedPerson.validatedModules?.split(".")?.contains(moduleId.toString()) == true

                        // MODULES
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                            ) {

                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = "${i.title}",
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )

                                Switch(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    colors = SwitchDefaults.colors(
                                        uncheckedThumbColor = NotValidatedModule,
                                        checkedThumbColor = ValidatedModule
                                    ),
                                    checked = isChecked,
                                    onCheckedChange = { isChecked ->
                                        val modId = i.id?.toInt() ?: return@Switch
                                        event.invoke(SubmitPersonEvent.OnPersonModulesSwitchToggle(modId, isChecked))
                                    }
                                )

                            }
                        }
                    }

                }

            }

        }
    }

}