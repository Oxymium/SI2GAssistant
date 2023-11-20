package com.oxymium.si2gassistant.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oxymium.si2gassistant.ui.scenes.AppScreens
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun BottomNavigationBar(
    navController: NavController,
    navigationEvent: (NavigationEvent) -> Unit
) {

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    ) {

        BottomNavigationItem(
            selected = navController.currentDestination?.route == AppScreens.GREETINGS_SCREEN.name,
            onClick = {
                navigationEvent.invoke(NavigationEvent.OnGreetingsButtonClicked)
            },
            icon = {
                Icon(Icons.Default.Favorite, contentDescription = null)
            },
            label = {
                Text("Greetings")
            }
        )

        BottomNavigationItem(
            selected = navController.currentDestination?.route == AppScreens.BUG_TICKETS_SCREEN.name,
            onClick = {
                navigationEvent.invoke(NavigationEvent.OnBugTicketsButtonClicked)
            },
            icon = {
                Icon(Icons.Default.Home, contentDescription = null)
            },
            label = {
                Text("Bugs")
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    Si2GAssistantTheme {
        val navController = rememberNavController()
        BottomNavigationBar(navController = navController) {

        }
    }
}