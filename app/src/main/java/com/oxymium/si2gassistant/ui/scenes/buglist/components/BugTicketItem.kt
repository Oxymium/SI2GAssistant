package com.oxymium.si2gassistant.ui.scenes.buglist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.oxymium.si2gassistant.domain.entities.BugTicketPriority
import com.oxymium.si2gassistant.ui.theme.PriorityCritical
import com.oxymium.si2gassistant.ui.theme.PriorityHigh
import com.oxymium.si2gassistant.ui.theme.PriorityLow
import com.oxymium.si2gassistant.ui.theme.PriorityMedium
import com.oxymium.si2gassistant.ui.theme.ResolvedBugTicket
import com.oxymium.si2gassistant.utils.DateUtils
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.UnresolvedBugTicket
import com.oxymium.si2gassistant.ui.theme.White
import com.oxymium.si2gassistant.ui.theme.White75
import com.oxymium.si2gassistant.utils.CapitalizeFirstLetter

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
                    BugTicketPriority.LOW -> PriorityLow
                    BugTicketPriority.MEDIUM -> PriorityMedium
                    BugTicketPriority.HIGH -> PriorityHigh
                    BugTicketPriority.CRITICAL -> PriorityCritical
                    else -> PriorityCritical
                },
                shape = MaterialTheme.shapes.medium
            )
            .clickable {
                onEvent(BugTicketListEvent.SelectBugTicket(bugTicket))
            }
    ) {

        Box(
            modifier = Modifier
                .rotate(180f)
                .matchParentSize()
                .wrapContentHeight()
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.grid_hexagons_items),
                colorFilter = ColorFilter.tint(White75),
                contentDescription = ""
            )
        }

        Column(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {

            Row {

                Icon(
                    modifier = Modifier
                        .background(
                            color = White,
                            shape = RoundedCornerShape(200.dp)
                        ),
                    painter = if (bugTicket.isResolved) painterResource(R.drawable.ic_check_circle) else painterResource(R.drawable.ic_help_circle),
                    contentDescription = "",
                    tint = if (bugTicket.isResolved) ResolvedBugTicket else UnresolvedBugTicket
                )

                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 8.dp),
                    text = CapitalizeFirstLetter.capitalizeFirstLetter(bugTicket.category.toString()),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }

            Text(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                text = DateUtils.convertMillisToDate(bugTicket.submittedDate ?: 0),
                color = Color.White,
                textAlign = TextAlign.Center,
            )

            Text(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                text = bugTicket.shortDescription ?: "Description",
                color = Color.White,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .wrapContentSize()
                        .background(
                            color = White75,
                            RoundedCornerShape(20.dp)
                        )
                        .align(Alignment.Center)
                ) {

                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        text = bugTicket.academy.toString(),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.End)
                    .padding(horizontal = 8.dp),
                text = bugTicket.priority?.name ?: "",
                color = Color.White,
                fontWeight = FontWeight.Bold
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