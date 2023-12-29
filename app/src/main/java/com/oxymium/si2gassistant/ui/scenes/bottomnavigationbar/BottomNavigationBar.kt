package com.oxymium.si2gassistant.ui.scenes.bottomnavigationbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.entities.User
import com.oxymium.si2gassistant.ui.App
import com.oxymium.si2gassistant.ui.scenes.AppScreens
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.scenes.NavigationState
import com.oxymium.si2gassistant.ui.theme.MenuAccent
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.NeutralLighter
import com.oxymium.si2gassistant.ui.theme.Orange500
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun BottomNavigationBar(
    navController: NavController?,
    navigationEvent: (NavigationEvent) -> Unit,
    navigationState: NavigationState
) {

    val backgroundColor = if (navigationState.navigationScreen == AppScreens.GREETINGS_SCREEN.name ||
        navigationState.navigationScreen == AppScreens.METRICS_SCREEN.name) Neutral else White

    Column(
        modifier = Modifier
            .background(
                color = backgroundColor
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = MenuAccent,
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    )
                )
        ) {

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                text = when (navigationState.navigationScreen) {
                    // BOTH
                    AppScreens.GREETINGS_SCREEN.name -> "Greetings"
                    // NORMAL
                    AppScreens.SUBMIT_PERSON_SCREEN.name -> "Persons"
                    AppScreens.REPORT_BUG_SCREEN.name -> "Bug tickets"
                    AppScreens.SUBMIT_SUGGESTION_SCREEN.name -> "Suggestion"
                    // SUPER
                    AppScreens.METRICS_SCREEN.name -> "Metrics"
                    AppScreens.PERSONS_SCREEN.name -> "Persons"
                    AppScreens.BUG_TICKETS_SCREEN.name -> "Bug tickets"
                    AppScreens.REPORT_BUG_SCREEN.name -> "Report a bug"
                    AppScreens.SUGGESTIONS_SCREEN.name -> "Suggestions"
                    else -> ""
                },
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

        }


        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            backgroundColor = Neutral,
            contentColor = Neutral
        ) {

            if (navigationState.currentUser?.hasAdministrativeRights == false) {

                // -----------------
                // NORMAL USER ITEMS
                // -----------------

                // GREETINGS
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (navigationState.navigationScreen == AppScreens.GREETINGS_SCREEN.name) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreens.GREETINGS_SCREEN.name,
                    onClick = {
                        navigationEvent.invoke(NavigationEvent.OnGreetingsButtonClick)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_account),
                            tint = White,
                            contentDescription = null
                        )
                    },
                )

                // SUBMIT PERSON
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (navigationState.navigationScreen == AppScreens.SUBMIT_PERSON_SCREEN.name) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreens.SUBMIT_PERSON_SCREEN.name,
                    onClick = {
                        navigationEvent.invoke(NavigationEvent.OnSubmitPersonButtonClick)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_account_plus),
                            tint = White,
                            contentDescription = null
                        )
                    },
                )

                // REPORT BUG
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (navigationState.navigationScreen == AppScreens.REPORT_BUG_SCREEN.name) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreens.REPORT_BUG_SCREEN.name,
                    onClick = {
                        navigationEvent.invoke(NavigationEvent.OnReportBugButtonClick)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bug),
                            tint = White,
                            contentDescription = null
                        )
                    },
                )

                // SUBMIT SUGGESTION
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (navigationState.navigationScreen == AppScreens.SUBMIT_SUGGESTION_SCREEN.name) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreens.SUBMIT_SUGGESTION_SCREEN.name,
                    onClick = {
                        navigationEvent.invoke(NavigationEvent.OnSubmitSuggestionButtonClick)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_comment_plus),
                            tint = White,
                            contentDescription = null
                        )
                    },
                )


                // -----------------
                // SUPER USER ITEMS
                // -----------------

            } else {

                // GREETINGS
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (navigationState.navigationScreen == AppScreens.GREETINGS_SCREEN.name) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreens.GREETINGS_SCREEN.name,
                    onClick = {
                        navigationEvent.invoke(NavigationEvent.OnGreetingsButtonClick)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_account),
                            tint = White,
                            contentDescription = null
                        )
                    },
                )

                // METRICS
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (navigationState.navigationScreen == AppScreens.METRICS_SCREEN.name) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreens.METRICS_SCREEN.name,
                    onClick = {
                        navigationEvent.invoke(NavigationEvent.OnMetricsButtonClick)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chart_box_outline),
                            tint = White,
                            contentDescription = null
                        )
                    },
                )

                // PERSONS
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (navigationState.navigationScreen == AppScreens.PERSONS_SCREEN.name) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreens.PERSONS_SCREEN.name,
                    onClick = {
                        navigationEvent.invoke(NavigationEvent.OnPersonsButtonClick)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_account_group),
                            tint = White,
                            contentDescription = null
                        )
                    },

                    )

                // BUG TICKETS
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (navigationState.navigationScreen == AppScreens.BUG_TICKETS_SCREEN.name) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreens.BUG_TICKETS_SCREEN.name,
                    onClick = {
                        navigationEvent.invoke(NavigationEvent.OnBugTicketsButtonClick)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bug),
                            tint = White,
                            contentDescription = null
                        )
                    },

                    )

                // BUG TICKETS
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (navigationState.navigationScreen == AppScreens.SUGGESTIONS_SCREEN.name) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreens.SUGGESTIONS_SCREEN.name,
                    onClick = {
                        navigationEvent.invoke(NavigationEvent.OnSuggestionsButtonClick)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_comment_text_multiple),
                            tint = White,
                            contentDescription = null
                        )
                    },

                    )

            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    Si2GAssistantTheme {
        val previewState = NavigationState(currentUser = User("", "", "", "", false))
        BottomNavigationBar(
            navController = null,
            {},
            navigationState = previewState)
    }
}