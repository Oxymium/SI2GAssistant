package com.oxymium.si2gassistant.ui.scenes.buglist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
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
import com.oxymium.si2gassistant.domain.usecase.BugTicketListEvent
import com.oxymium.si2gassistant.domain.usecase.BugTicketListState
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Orange500
import com.oxymium.si2gassistant.ui.theme.PriorityCritical
import com.oxymium.si2gassistant.ui.theme.PriorityHigh
import com.oxymium.si2gassistant.ui.theme.PriorityLow
import com.oxymium.si2gassistant.ui.theme.PriorityMedium
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun BugTicketSearch(
    state: BugTicketListState,
    event: (BugTicketListEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = Neutral,
                shape = RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            )
    ) {

        // SEARCH FIELD
        Row(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Neutral
                )
        ) {

            var search by remember { mutableStateOf("") }

            Box(
                modifier = Modifier
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = search,
                    onValueChange = {
                        search = it.filter { char -> !char.isWhitespace() }.take(20)
                        event.invoke(BugTicketListEvent.OnSearchTextInput(search))
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Orange500,
                        cursorColor = White,
                        focusedBorderColor = Orange500,
                        unfocusedBorderColor = White
                    ),
                    label = {
                        androidx.compose.material.Text(
                            text = when(state.bugTickets.size) {
                                0 -> "Quick search: 0 results"
                                1 -> "Quick search: 1 result"
                                else -> "Quick search: ${state.bugTickets.size} results"
                            },
                            color = Color.White
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

//        // BUTTONS
//        Row(
//            modifier = Modifier
//                .padding(8.dp)
//                .align(Alignment.CenterHorizontally)
//                .background(
//                    color = Color.White,
//                    shape = CircleShape
//                )
//        ) {
//
//            // Button: refresh
//            Box(
//                modifier = Modifier
//                    .padding(1.dp)
//                    .wrapContentSize()
//                    .background(
//                        color = White,
//                        shape = CircleShape
//                    )
//                    .clickable {
//                        event.invoke(BugTicketListEvent.OnRefreshButtonClick)
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_refresh),
//                    tint = Neutral,
//                    contentDescription = "Refresh"
//                )
//            }
//
//            // Button: priority low
//            Box(
//                modifier = Modifier
//                    .padding(1.dp)
//                    .wrapContentSize()
//                    .background(
//                        color = PriorityLow,
//                        shape = CircleShape
//                    )
//                    .clickable {
//                        event.invoke(BugTicketListEvent.OnLowPriorityButtonClick)
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_circle),
//                    tint = PriorityLow,
//                    contentDescription = "Low Priority"
//                )
//            }
//
//            // Button: priority medium
//            Box(
//                modifier = Modifier
//                    .padding(1.dp)
//                    .wrapContentSize()
//                    .background(
//                        color = PriorityMedium,
//                        shape = CircleShape
//                    )
//                    .clickable {
//                        event.invoke(BugTicketListEvent.OnMediumPriorityButtonClick)
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_circle),
//                    tint = PriorityMedium,
//                    contentDescription = "Medium Priority"
//                )
//            }
//
//            // Button: priority high
//            Box(
//                modifier = Modifier
//                    .padding(1.dp)
//                    .wrapContentSize()
//                    .background(
//                        color = PriorityHigh,
//                        shape = CircleShape
//                    )
//                    .clickable {
//                        event.invoke(BugTicketListEvent.OnHighPriorityButtonClick)
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_circle),
//                    tint = PriorityHigh,
//                    contentDescription = "High Priority"
//                )
//            }
//
//            // Button: priority critical
//            Box(
//                modifier = Modifier
//                    .padding(1.dp)
//                    .wrapContentSize()
//                    .background(
//                        color = PriorityCritical,
//                        shape = CircleShape
//                    )
//                    .clickable {
//                        event.invoke(BugTicketListEvent.OnCriticalPriorityButtonClick)
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_circle),
//                    tint = PriorityCritical,
//                    contentDescription = "Critical Priority"
//                )
//            }
//
//            // Button: random insert for debug purposes
//            Box(
//                modifier = Modifier
//                    .padding(1.dp)
//                    .wrapContentSize()
//                    .background(
//                        color = White,
//                        shape = CircleShape
//                    )
//                    .clickable {
//                        event.invoke(BugTicketListEvent.GenerateRandomBugTicket)
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_dice_multiple),
//                    tint = Neutral,
//                    contentDescription = "Random"
//                )
//            }

    }
}

@Preview(showBackground = true)
@Composable
fun BugTicketSearchPreview() {
    Si2GAssistantTheme {
        val previewState = BugTicketListState()
        BugTicketSearch(state = previewState){}
    }
}