package com.oxymium.si2gassistant.ui.scenes.bugtickets.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.entities.BugTicketPriority
import com.oxymium.si2gassistant.domain.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.states.BugTicketsState
import com.oxymium.si2gassistant.ui.scenes.animations.UploadingAnimation
import com.oxymium.si2gassistant.ui.scenes.bugtickets.BugTicketsEvent
import com.oxymium.si2gassistant.ui.theme.Black
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.NeutralLighter
import com.oxymium.si2gassistant.ui.theme.PriorityCritical
import com.oxymium.si2gassistant.ui.theme.PriorityHigh
import com.oxymium.si2gassistant.ui.theme.PriorityLow
import com.oxymium.si2gassistant.ui.theme.PriorityMedium
import com.oxymium.si2gassistant.ui.theme.ResolvedBugTicket
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.UnresolvedBugTicket
import com.oxymium.si2gassistant.ui.theme.White
import com.oxymium.si2gassistant.utils.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BugTicketBottomSheet(
    state: BugTicketsState,
    event: (BugTicketsEvent) -> Unit
) {

    if (state.selectedBugTicket != null) {

        val backgroundColor = when (state.selectedBugTicket.priority) {
            BugTicketPriority.LOW -> PriorityLow
            BugTicketPriority.MEDIUM -> PriorityMedium
            BugTicketPriority.HIGH -> PriorityHigh
            BugTicketPriority.CRITICAL -> PriorityCritical
            else -> PriorityCritical
        }

        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(0.95f),
            containerColor = Neutral,
            onDismissRequest = { event.invoke(BugTicketsEvent.DismissBugTicketDetailsSheet)
            }
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

                    // CATEGORY
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "category: ${state.selectedBugTicket.category?.name}",
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

            // PRE INFO
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {

                    // PRIORITY
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "priority: ${state.selectedBugTicket.priority?.name}",
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

            // HEADER
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

                    // SUBMITTED DATE
                    val formattedDate =
                        DateUtils.convertMillisToDate(state.selectedBugTicket.submittedDate)
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "when: $formattedDate",
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    // SUBMITTED BY
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "by: ${state.selectedBugTicket.submittedBy}",
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    // ACADEMY
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "where: ${state.selectedBugTicket.academy}",
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

            // BODY
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

                    // SHORT DESCRIPTION
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = state.selectedBugTicket.shortDescription ?: "",
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    // DESCRIPTION
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = state.selectedBugTicket.description ?: "",
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

            // RESOLVED
            if (state.selectedBugTicket.isResolved) {

                // BODY
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(0.9f)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)
                        .background(
                            color = ResolvedBugTicket,
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ) {

                        // RESOLVED DATE
                        val formattedDate =
                            DateUtils.convertMillisToDate(state.selectedBugTicket.resolvedDate)
                        Text(
                            modifier = Modifier
                                .padding(2.dp)
                                .align(Alignment.CenterHorizontally),
                            text = "resolved: $formattedDate",
                            color = White,
                            textAlign = TextAlign.Center
                        )

                        // RESOLVED COMMENT
                        Text(
                            modifier = Modifier
                                .padding(2.dp)
                                .align(Alignment.CenterHorizontally),
                            text = state.selectedBugTicket.resolvedComment.toString(),
                            color = White,
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }

            if (!state.selectedBugTicket.isResolved) {

                // BODY
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(0.9f)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)
                        .background(
                            color = UnresolvedBugTicket,
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ) {

                        // RESOLVED DATE
                        Text(
                            modifier = Modifier
                                .padding(2.dp)
                                .align(Alignment.CenterHorizontally),
                            text = "unresolved",
                            color = White,
                            textAlign = TextAlign.Center
                        )

                        // RESOLVED COMMENT
                        var resolvedComment by remember { mutableStateOf("") }
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                        ) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = resolvedComment,
                                onValueChange = {
                                    val resolvedCommentTrimmed = it.trim()
                                    if (resolvedCommentTrimmed.isNotBlank() || it.isEmpty()) {
                                        resolvedComment = it.take(100)
                                        event.invoke(
                                            BugTicketsEvent.OnResolvedCommentChange(
                                                resolvedComment
                                            )
                                        )
                                    }
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Black,
                                    cursorColor = White,
                                    focusedBorderColor = Black,
                                    unfocusedBorderColor = White
                                ),
                                label = {
                                    Text(
                                        text = "Resolved comment",
                                        color = Color.White
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Email
                                ),
                                minLines = 3,
                                maxLines = 3
                            )
                        }

                        if (state.isResolvedCommentFieldError) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .fillMaxWidth(),
                                text = "*cannot be empty",
                                color = White,
                                textAlign = TextAlign.End
                            )
                        }

                        if (state.isUpdateBugTicketLoading) {

                            UploadingAnimation()

                        } else {

                            // BUTTON: SUBMIT PERSON MODE
                            Button(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .align(Alignment.CenterHorizontally),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Neutral
                                ),
                                onClick = {
                                    event.invoke(
                                        BugTicketsEvent.OnResolvedDetailsSheetButtonClick(
                                            state.selectedBugTicket
                                        )
                                    )
                                }
                            ) {

                                Icon(
                                    modifier = Modifier
                                        .background(color = Neutral)
                                        .size(24.dp),
                                    painter = painterResource(id = R.drawable.ic_cloud_upload),
                                    contentDescription = null,
                                    tint = White
                                )
                            }

                        }

                    }
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun BugTicketBottomSheetPreview() {
    val statePreview = BugTicketsState(
        selectedBugTicket = provideRandomBugTicket(),
        isSelectedBugTicketDetailsOpen = true)
    Si2GAssistantTheme {
        BugTicketBottomSheet(statePreview){}
    }
}