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
import com.oxymium.si2gassistant.ui.theme.Metrics6
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun OverallMetricsScreen(
    state: MetricsState
) {

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(
                color = Color.White
            )
            .verticalScroll(rememberScrollState())
    ) {

        // -----
        // USERS
        // -----

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
                text = "Users",
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
                    text = "${state.users?.size}",
                    color = Black,
                    fontWeight = FontWeight.Bold
                )

            }

        }

        // -------
        // PERSONS
        // -------

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
                text = "Persons",
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
                    text = "${state.persons?.size}",
                    color = Black,
                    fontWeight = FontWeight.Bold
                )

            }

        }

        // ---------
        // ACADEMIES
        // ---------

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
                text = "Academies",
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
                    text = "${state.academies?.size}",
                    color = Black,
                    fontWeight = FontWeight.Bold
                )

            }

        }

        // -------
        // MODULES
        // -------

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
                text = "Modules",
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
                    text = "${state.modules?.size}",
                    color = Black,
                    fontWeight = FontWeight.Bold
                )

            }


        }

        // -----------
        // BUG TICKETS
        // -----------

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

        // -----------
        // SUGGESTIONS
        // -----------

        Column(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .background(
                    color = Metrics6,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Suggestions",
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
                    text = "${state.suggestions?.size}",
                    color = Black,
                    fontWeight = FontWeight.Bold
                )

            }


        }

    }

}

@Preview(showBackground = true)
@Composable
fun OverallMetricsScreenPreview() {
    Si2GAssistantTheme {
        val previewState = MetricsState(
            bugTickets = List(20) { provideRandomBugTicket() }
        )
        OverallMetricsScreen(
            state = previewState
        )
    }

}

