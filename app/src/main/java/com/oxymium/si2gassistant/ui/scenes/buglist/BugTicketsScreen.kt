package com.oxymium.si2gassistant.ui.scenes.buglist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.usecase.BugTicketListEvent
import com.oxymium.si2gassistant.domain.usecase.BugTicketListState
import com.oxymium.si2gassistant.ui.scenes.buglist.components.BugTicketBottomSheet
import com.oxymium.si2gassistant.ui.scenes.buglist.components.BugTicketList
import com.oxymium.si2gassistant.ui.scenes.buglist.components.BugTicketSearch
import com.oxymium.si2gassistant.ui.scenes.loading.LoadingAnimation
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun BugTicketsScreen(
    state: BugTicketListState,
    event: (BugTicketListEvent) ->  Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            BugTicketSearch(
                state = state,
                event
            )

            if (state.isBugTicketsLoading) {

                LoadingAnimation()

            } else {

                BugTicketList(
                    state = state,
                    event
                )

            }

        }
    }

    BugTicketBottomSheet(
        state = state,
        event
    )

}

@Preview(showBackground = true)
@Composable
fun BugTicketsScreenPreview() {
    Si2GAssistantTheme {
        val previewState = BugTicketListState(
            bugTickets = List(20) { provideRandomBugTicket() },
            isBugTicketsLoading = false
        )
        BugTicketsScreen(
            state = previewState
        ) {

        }
    }
}