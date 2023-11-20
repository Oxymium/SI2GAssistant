package com.oxymium.si2gassistant.ui.scenes.buglist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.usecase.BugTicketListEvent
import com.oxymium.si2gassistant.ui.theme.BugTicketPriority
import com.oxymium.si2gassistant.ui.theme.DateUtils
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import java.time.format.TextStyle

@Composable
fun BugTicketItem(
    bugTicket: BugTicket,
    onEvent: (BugTicketListEvent) -> Unit,
) {
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color =
                when (bugTicket.priority) {
                    BugTicketPriority.LOW -> Color(0xFF2E8B57)
                    BugTicketPriority.MEDIUM -> Color(0xFF00FF00)
                    BugTicketPriority.HIGH -> Color(0xFF228B22)
                    BugTicketPriority.CRITICAL -> Color(0xFF006400)
                },
                shape = MaterialTheme.shapes.medium
            )
            .clickable {
                onEvent(BugTicketListEvent.SelectBugTicket(bugTicket))
            }
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                painter = if (bugTicket.isResolved) painterResource(R.drawable.ic_check_circle) else painterResource(R.drawable.ic_help_circle),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = bugTicket.category.toString(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = bugTicket.shortDescription ?: "Description",
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = DateUtils.convertMillisToDate(bugTicket.submittedDate ?: 0),
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = bugTicket.academy.toString(),
                color = Color.White,
                textAlign = TextAlign.Center
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun BugTicketItemPreview() {
    val bugTicketPreview = provideRandomBugTicket()
    Si2GAssistantTheme {
        BugTicketItem(bugTicketPreview){
        }
    }
}