package com.oxymium.si2gassistant.ui.scenes.buglist

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.Si2gAssistantApp
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.usecase.BugTicketListEvent
import com.oxymium.si2gassistant.domain.usecase.BugTicketListState
import com.oxymium.si2gassistant.ui.scenes.greetings.GreetingsViewModel
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun BugTicketsScreen() {

    val viewModel = koinViewModel<BugTicketViewModel>()
    val state = viewModel.state.collectAsState()

    BugTicketList(
        state = state.value ,
        viewModel::onEvent
    )

}

@Preview(showBackground = true)
@Composable
fun BugTicketsScreenPreview() {
    val bugTicketsPreview = List(10){ provideRandomBugTicket() }
    val statePreview = BugTicketListState(bugTicketsPreview, null)
    Si2GAssistantTheme {
    }
}