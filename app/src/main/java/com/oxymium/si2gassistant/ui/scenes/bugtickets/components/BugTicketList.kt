package com.oxymium.si2gassistant.ui.scenes.bugtickets.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.domain.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.states.BugTicketsState
import com.oxymium.si2gassistant.ui.scenes.bugtickets.BugTicketsEvent
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun BugTicketList(
    state: BugTicketsState,
    onEvent: (BugTicketsEvent) -> Unit
) {
    Column {
        
        LazyColumn(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.bugTickets) { bugTicket ->
                BugTicketItem(
                    bugTicket = bugTicket,
                    onEvent = onEvent
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun BugTicketListPreview() {
    Si2GAssistantTheme {
        val bugTicketsPreview = List(10){ provideRandomBugTicket() }
        val statePreview = BugTicketsState(bugTicketsPreview, null)
        BugTicketList(statePreview) { }
    }
}