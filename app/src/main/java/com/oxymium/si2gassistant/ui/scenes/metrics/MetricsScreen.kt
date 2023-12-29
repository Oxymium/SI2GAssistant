package com.oxymium.si2gassistant.ui.scenes.metrics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.domain.entities.mock.ALL_ACADEMIES
import com.oxymium.si2gassistant.domain.entities.mock.ALL_MODULES
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomSuggestion
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun MetricsScreen(
    state: MetricsState
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Neutral
            )
    ) {

        // -----------
        // BUG TICKETS
        // -----------

        val bugTicketListSize = state.bugTicketList?.size ?: 0
        val bugTicketListMostCategory = state.bugTicketList?.groupingBy { it.category }?.eachCount()?.maxByOrNull { it.value }?.key
        val bugTicketListMostPriority = state.bugTicketList?.groupingBy { it.priority }?.eachCount()?.maxByOrNull { it.value }?.key
        val bugTicketListResolved = state.bugTicketList?.count { it.isResolved } ?: 0
        val percentageResolved = if (bugTicketListSize > 0) {
            (bugTicketListResolved.toDouble() / bugTicketListSize * 100).toInt()
        }else {
            0
        }
        
        Text(
            text = "Total amount of bug tickets reported",
            color = Color.White
        )

        Text(
            modifier = Modifier.
            align(Alignment.End),
            text = "$bugTicketListSize",
            color = Color.White
        )

        Text(
            text  = "Most represented category",
            color = Color.White
        )

        Text(
            modifier = Modifier.
            align(Alignment.End),
            text = "$bugTicketListMostCategory",
            color = Color.White
        )

        Text(
            text  = "Most represented priority",
            color = Color.White
        )

        Text(
            modifier = Modifier.
            align(Alignment.End),
            text = "$bugTicketListMostPriority",
            color = Color.White
        )

        Text(
            text  = "Resolved tickets",
            color = Color.White
        )

        Text(
            modifier = Modifier.
            align(Alignment.End),
            text = "$bugTicketListResolved/$bugTicketListSize ($percentageResolved %)",
            color = Color.White
        )

        // ---------
        // ACADEMIES
        // ---------

        val academyThatProducesTheMostTickets = state.bugTicketList?.groupingBy { it.academy }?.eachCount()?.maxByOrNull { it.value }?.key


        Text(
            text = "Total amount of academies",
            color = Color.White
        )

        Text(
            modifier = Modifier.
            align(Alignment.End),
            text = "${state.academies?.size}",
            color = Color.White
        )
        
        Text(
            text = "Academy that produces the most tickets",
            color = Color.White
        )

        Text(
            modifier = Modifier.
            align(Alignment.End),
            text = "$academyThatProducesTheMostTickets",
            color = Color.White
        )

        // -------
        // MODULES
        // -------

        Text(
            text = "Total amount of modules",
            color = Color.White
        )

        Text(
            modifier = Modifier.
            align(Alignment.End),
            text = "${state.modules?.size}",
            color = Color.White
        )

        // -----
        // USERS
        // -----

        Text(
            text = "Total amount of users",
            color = Color.White
        )

        Text(
            modifier = Modifier.
            align(Alignment.End),
            text = "${state.users?.size}",
            color = Color.White
        )


        // -------
        // PERSONS
        // -------

        Text(
            text = "Total amount of persons",
            color = Color.White
            )

        Text(
            modifier = Modifier.
            align(Alignment.End),
            text = "${state.persons?.size}",
            color = Color.White
        )

        // -----------
        // SUGGESTIONS
        // -----------
        
        Text(
            text = "Total amount of suggestions",
            color = Color.White
        )

        Text(
            modifier = Modifier.
            align(Alignment.End),
            text = "${state.suggestions?.size}",
            color = Color.White
        )

    }

}

@Preview(showBackground = true)
@Composable
fun MetricsScreenPreview() {
    val statePreview = MetricsState(
        bugTicketList =  List(10) { provideRandomBugTicket() },
        academies = ALL_ACADEMIES,
        modules = ALL_MODULES,
        users = null,
        persons = null,
        suggestions = List(10) { provideRandomSuggestion() }
    )
    Si2GAssistantTheme {
        MetricsScreen(statePreview)
    }
}