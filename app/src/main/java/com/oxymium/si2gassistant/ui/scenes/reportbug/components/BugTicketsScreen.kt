package com.oxymium.si2gassistant.ui.scenes.reportbug.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.ui.scenes.animations.LoadingAnimation
import com.oxymium.si2gassistant.ui.scenes.buglist.components.BugTicketItem
import com.oxymium.si2gassistant.ui.scenes.reportbug.ReportBugEvent
import com.oxymium.si2gassistant.ui.scenes.reportbug.ReportBugState
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun BugTicketsScreen(
    state: ReportBugState,
    event: (ReportBugEvent) -> Unit
) {

    if (state.isBugTicketsLoading) {

        LoadingAnimation(
            modifier = Modifier
                .fillMaxSize()
        )

    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.bugTickets) { bugTicket ->
                    BugTicketItem(
                        bugTicket = bugTicket
                    ) {
                    }
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun BugTicketsScreenPreview() {
    Si2GAssistantTheme {
        val statePreview = ReportBugState()
        BugTicketsScreen(
            state = statePreview
        ) {}
    }
}