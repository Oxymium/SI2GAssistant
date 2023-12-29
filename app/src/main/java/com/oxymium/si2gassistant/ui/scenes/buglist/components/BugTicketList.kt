package com.oxymium.si2gassistant.ui.scenes.buglist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.usecase.BugTicketListEvent
import com.oxymium.si2gassistant.domain.usecase.BugTicketListState
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun BugTicketList(
    state: BugTicketListState,
    onEvent: (BugTicketListEvent) -> Unit
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
        val statePreview = BugTicketListState(bugTicketsPreview, null)
        BugTicketList(statePreview){
            Unit
        }
    }
}