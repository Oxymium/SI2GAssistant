package com.oxymium.si2gassistant.ui.scenes.reportbug

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.oxymium.si2gassistant.domain.states.ReportBugState
import com.oxymium.si2gassistant.ui.scenes.reportbug.components.BugTicketsScreen
import com.oxymium.si2gassistant.ui.scenes.reportbug.components.ReportBugScreenTest
import com.oxymium.si2gassistant.ui.theme.MenuAccent
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun ReportBugScreen(
    state: ReportBugState,
    event: (ReportBugEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Neutral,
                    shape = RoundedCornerShape(
                        bottomStart = 25.dp,
                        bottomEnd = 25.dp
                    )
                )
        ) {

            // BUTTON: BUG TICKETS
            Button(
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.bugTicketsMode) MenuAccent else White
                ),
                onClick = { event.invoke(ReportBugEvent.OnBugTicketsModeButtonClick) }
            ) {

                Icon(
                    modifier = Modifier
                        .background(if (state.bugTicketsMode) MenuAccent else White)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_bug),
                    contentDescription = null,
                    tint = if (state.bugTicketsMode) White else Neutral
                )
            }

            // BUTTON: REPORT BUG TICKET MODE
            Button(
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.submitBugTicketMode) MenuAccent else White
                ),
                onClick = { event.invoke(ReportBugEvent.OnReportBugModeButtonClick) }
            ) {

                Icon(
                    modifier = Modifier
                        .background(if (state.submitBugTicketMode) MenuAccent else White)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_plus_thick),
                    contentDescription = null,
                    tint = if (state.submitBugTicketMode) White else Neutral
                )
            }

            // TITLE
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(),
                text = if (state.bugTicketsMode) "Your bug tickets" else "Submit bug ticket",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }

        if (state.bugTicketsMode) {
            BugTicketsScreen(
                state = state
            )
        }

        if (state.submitBugTicketMode) {
            ReportBugScreenTest(
                state = state,
                event = event
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun ReportBugScreenPreview() {
    Si2GAssistantTheme {
        val previewState = ReportBugState(
            submitBugTicketMode = false,
            bugTicketsMode = true
        )
        ReportBugScreen(
            state = previewState
        ) {}
    }
}