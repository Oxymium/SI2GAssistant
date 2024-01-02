package com.oxymium.si2gassistant.ui.scenes.metrics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.ui.scenes.metrics.MetricsState
import com.oxymium.si2gassistant.ui.theme.Black
import com.oxymium.si2gassistant.ui.theme.Metrics1
import com.oxymium.si2gassistant.ui.theme.Metrics2
import com.oxymium.si2gassistant.ui.theme.Metrics3
import com.oxymium.si2gassistant.ui.theme.Metrics4
import com.oxymium.si2gassistant.ui.theme.Metrics5
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White
import com.oxymium.si2gassistant.utils.CapitalizeFirstLetter
import kotlin.math.round

@Composable
fun BugTicketsMetricsScreen(
    state: MetricsState
) {

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(
                color = White
            )
            .verticalScroll(rememberScrollState())

    ) {

        val bugTicketListSize = state.bugTickets?.size ?: 0
        val bugTicketListResolved = state.bugTickets?.count { it.isResolved } ?: 0
        val percentageResolved = if (bugTicketListSize > 0) {
            (bugTicketListResolved.toDouble() / bugTicketListSize * 100).toInt()
        } else {
            0
        }

        // -----------
        // BUG TICKETS
        // -----------

        Column(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .background(
                    color = Metrics1,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Bug tickets",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {

                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "${state.bugTickets?.size}",
                    color = Black,
                    fontWeight = FontWeight.Bold
                )

            }

        }

        // --------
        // RESOLVED
        // --------

        Column(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .background(
                    color = Metrics2,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Resolved",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {

                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "$bugTicketListResolved/$bugTicketListSize ($percentageResolved %)",
                    color = Black,
                    fontWeight = FontWeight.Bold
                )

            }

        }

        // --------
        // CATEGORY
        // --------

        // Group by category and count occurrences
        val categoryCounts = state.bugTickets?.groupBy { it.category }?.mapValues { it.value.size }

        // Sort categories by count in descending order
        val sortedCategories = categoryCounts?.entries?.sortedByDescending { it.value }

        Column(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .background(
                    color = Metrics3,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Categories",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {

                Column {

                    if (sortedCategories != null) {
                        for ((category, count) in sortedCategories) {

                            val percentage = round((count / bugTicketListSize.toFloat()) * 100 * 100) / 100 // rounded
                            val categoryCapitalized = CapitalizeFirstLetter.capitalizeFirstLetter(category?.name ?: "")
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = "$categoryCapitalized: $count ($percentage%)",
                                color = Black,
                                fontWeight = FontWeight.Bold
                            )

                        }
                    }

                }

            }

        }

        // --------
        // PRIORITY
        // --------

        // Group by priority and count occurrences
        val priorityCounts = state.bugTickets?.groupBy { it.priority }?.mapValues { it.value.size }

        // Sort priorities by count in descending order
        val sortedPriorities = priorityCounts?.entries?.sortedByDescending { it.value }

        Column(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .background(
                    color = Metrics4,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Priorities",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {

                Column {

                    if (sortedPriorities != null) {
                        for ((priority, count) in sortedPriorities) {

                            val percentage = round((count / bugTicketListSize.toFloat()) * 100 * 100) / 100
                            val priorityCapitalized = CapitalizeFirstLetter.capitalizeFirstLetter(priority?.name ?: "")
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = "$priorityCapitalized: $count ($percentage%)",
                                color = Black,
                                fontWeight = FontWeight.Bold
                            )

                        }
                    }

                }

            }

        }

        // ----------------
        // TICKET PRODUCERS
        // ----------------

        // Group by academies and count occurrences
        val academyCounts = state.bugTickets?.groupBy { it.academy }?.mapValues { it.value.size }

        // Sort academies by count in descending order
        val sortedAcademies = academyCounts?.entries?.sortedByDescending { it.value }

        Column(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .background(
                    color = Metrics5,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Best ticket producers",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {

                Column {

                    if (sortedAcademies != null) {
                        for ((academy, count) in sortedAcademies) {

                            val percentage = round((count / bugTicketListSize.toFloat()) * 100 * 100) / 100
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = "$academy: $count ($percentage%)",
                                color = Black,
                                fontWeight = FontWeight.Bold
                            )

                        }
                    }

                }

            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun BugTicketsMetricsScreenPreview() {
    Si2GAssistantTheme {
        val previewState = MetricsState(
            bugTickets = List (250) { provideRandomBugTicket() }
        )
        BugTicketsMetricsScreen(
            state = previewState
        )
    }
}