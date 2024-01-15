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
import com.oxymium.si2gassistant.domain.states.AppState
import com.oxymium.si2gassistant.ui.routes.AppScreen
import com.oxymium.si2gassistant.ui.theme.MenuAccent
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun BottomNavigationBar(
    navController: NavController?,
    appState: AppState,
    onNavigateTo: (AppScreen) -> Unit
) {

    val backgroundColor = if (appState.currentScreen == AppScreen.GREETINGS_SCREEN) Neutral else White

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
                text = when (appState.currentScreen) {
                    // BOTH
                    AppScreen.GREETINGS_SCREEN -> "Greetings"
                    // NORMAL
                    AppScreen.SUBMIT_PERSON_SCREEN -> "Persons"
                    AppScreen.REPORT_BUG_SCREEN -> "Bug tickets"
                    AppScreen.SUBMIT_SUGGESTION_SCREEN -> "Suggestion"
                    AppScreen.CHAT_SCREEN -> "Chat"
                    // SUPER
                    AppScreen.METRICS_SCREEN -> "Metrics"
                    AppScreen.PERSONS_SCREEN -> "Persons"
                    AppScreen.BUG_TICKETS_SCREEN -> "Bug tickets"
                    AppScreen.SUGGESTIONS_SCREEN -> "Suggestions"
                    else -> "BLANK"
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

            if (appState.currentUser?.hasAdministrativeRights == false) {

                // -----------------
                // NORMAL USER ITEMS
                // -----------------

                // GREETINGS
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (appState.currentScreen == AppScreen.GREETINGS_SCREEN) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = appState.currentScreen == AppScreen.GREETINGS_SCREEN,
                    onClick = {
                        onNavigateTo(AppScreen.GREETINGS_SCREEN)
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
                            color = if (appState.currentScreen == AppScreen.SUBMIT_PERSON_SCREEN) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreen.SUBMIT_PERSON_SCREEN.name,
                    onClick = {
                        onNavigateTo(AppScreen.SUBMIT_PERSON_SCREEN)
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
                            color = if (appState.currentScreen == AppScreen.REPORT_BUG_SCREEN) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreen.REPORT_BUG_SCREEN.name,
                    onClick = {
                        onNavigateTo(AppScreen.REPORT_BUG_SCREEN)
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
                            color = if (appState.currentScreen == AppScreen.SUBMIT_SUGGESTION_SCREEN) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreen.SUBMIT_SUGGESTION_SCREEN.name,
                    onClick = {
                        onNavigateTo(AppScreen.SUBMIT_SUGGESTION_SCREEN)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lightbulb),
                            tint = White,
                            contentDescription = null
                        )
                    },
                )

                // CHAT
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (appState.currentScreen == AppScreen.CHAT_SCREEN) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreen.CHAT_SCREEN.name,
                    onClick = {
                        onNavigateTo(AppScreen.CHAT_SCREEN)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_forum_outline),
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
                            color = if (appState.currentScreen == AppScreen.GREETINGS_SCREEN) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = appState.currentScreen  == AppScreen.GREETINGS_SCREEN,
                    onClick = {
                        onNavigateTo(AppScreen.GREETINGS_SCREEN)
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
                            color = if (appState.currentScreen == AppScreen.METRICS_SCREEN) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreen.METRICS_SCREEN.name,
                    onClick = {
                        onNavigateTo(AppScreen.METRICS_SCREEN)
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
                            color = if (appState.currentScreen == AppScreen.PERSONS_SCREEN) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreen.PERSONS_SCREEN.name,
                    onClick = {
                        onNavigateTo(AppScreen.PERSONS_SCREEN)
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
                            color = if (appState.currentScreen == AppScreen.BUG_TICKETS_SCREEN) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreen.BUG_TICKETS_SCREEN.name,
                    onClick = {
                        onNavigateTo(AppScreen.BUG_TICKETS_SCREEN)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bug),
                            tint = White,
                            contentDescription = null
                        )
                    },

                    )

                // SUGGESTIONS
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (appState.currentScreen == AppScreen.SUGGESTIONS_SCREEN) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreen.SUGGESTIONS_SCREEN.name,
                    onClick = {
                        onNavigateTo(AppScreen.SUGGESTIONS_SCREEN)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lightbulb),
                            tint = White,
                            contentDescription = null
                        )
                    },

                    )

                // CHAT
                BottomNavigationItem(
                    modifier = Modifier
                        .background(
                            color = if (appState.currentScreen == AppScreen.CHAT_SCREEN) MenuAccent else Neutral,
                            shape = RoundedCornerShape(
                                bottomStart = 75.dp,
                                bottomEnd = 75.dp
                            )
                        ),
                    selected = navController?.currentDestination?.route == AppScreen.CHAT_SCREEN.name,
                    onClick = {
                        onNavigateTo(AppScreen.CHAT_SCREEN)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_forum_outline),
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
        val appState = AppState(User())
        BottomNavigationBar(
            navController = null,
            appState = appState
        ) {}
    }
}