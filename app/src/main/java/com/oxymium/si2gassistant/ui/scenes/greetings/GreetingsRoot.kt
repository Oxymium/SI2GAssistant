package com.oxymium.si2gassistant.ui.scenes.greetings

import androidx.compose.runtime.Composable
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.scenes.NavigationState


@Composable
fun GreetingsRoot(
    navigationState: NavigationState,
    greetingsState: GreetingsState,
    navigationEvent: (NavigationEvent) -> Unit
) {

    // Access navigation state
    if (navigationState.navigationRoute == "Route") { /* DO */ }

    GreetingsScreen(
        state = greetingsState,
        navigationEvent = navigationEvent
    )

}