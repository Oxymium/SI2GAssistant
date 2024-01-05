package com.oxymium.si2gassistant.ui.scenes.metrics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.mock.ALL_ACADEMIES
import com.oxymium.si2gassistant.domain.mock.ALL_MODULES
import com.oxymium.si2gassistant.domain.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.mock.provideRandomSuggestion
import com.oxymium.si2gassistant.domain.states.MetricsState
import com.oxymium.si2gassistant.ui.scenes.metrics.components.BugTicketsMetricsScreen
import com.oxymium.si2gassistant.ui.scenes.metrics.components.OverallMetricsScreen
import com.oxymium.si2gassistant.ui.theme.MenuAccent
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun MetricsScreen(
    state: MetricsState,
    event: (MetricsScreenEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = White
            )
    ) {

        // METRICS MENU
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Neutral,
                    shape = RoundedCornerShape(
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                )
        ) {

            // BUTTON: OVERALL METRICS
            Button(
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.isOverallMetricsScreen) MenuAccent else White
                ),
                onClick = { event.invoke(MetricsScreenEvent.OnOverallMetricsButtonClick) }
            ) {

                Icon(
                    modifier = Modifier
                        .background(if (state.isOverallMetricsScreen) MenuAccent else White)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_bug),
                    contentDescription = null,
                    tint = if (state.isOverallMetricsScreen) White else Neutral
                )
            }

            // BUTTON: BUG TICKETS
            Button(
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.isBugTicketMetricsScreen) MenuAccent else White
                ),
                onClick = { event.invoke(MetricsScreenEvent.OnBugTicketsButtonClick) }
            ) {

                Icon(
                    modifier = Modifier
                        .background(if (state.isBugTicketMetricsScreen) MenuAccent else White)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_bug),
                    contentDescription = null,
                    tint = if (state.isBugTicketMetricsScreen) White else Neutral
                )
            }

            // TITLE
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(),
                text = if (state.isOverallMetricsScreen) "Overall" else "Bug tickets",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

        }

        if (state.isOverallMetricsScreen) OverallMetricsScreen(state = state)
        
        if (state.isBugTicketMetricsScreen) BugTicketsMetricsScreen(state = state)


        }


}

@Preview(showBackground = true)
@Composable
fun MetricsScreenPreview() {
    val statePreview = MetricsState(
        bugTickets =  List(10) { provideRandomBugTicket() },
        academies = ALL_ACADEMIES,
        modules = ALL_MODULES,
        users = null,
        persons = null,
        suggestions = List(10) { provideRandomSuggestion() }
    )
    Si2GAssistantTheme {
        MetricsScreen(statePreview) {}
    }
}