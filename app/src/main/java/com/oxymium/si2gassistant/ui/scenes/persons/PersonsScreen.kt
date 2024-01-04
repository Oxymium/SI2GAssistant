package com.oxymium.si2gassistant.ui.scenes.persons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomPerson
import com.oxymium.si2gassistant.ui.scenes.animations.LoadingAnimation
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonList
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonListEvent
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonListState
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonSearch
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun PersonsScreen(
    state: PersonListState,
    event: (PersonListEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        PersonSearch(
            state = state,
            event = event
        )

        if (state.isPersonsLoading) {

            LoadingAnimation(
                modifier = Modifier
                    .fillMaxSize()
            )

        } else {

            PersonList(
                state = state,
                event = event
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun PersonsScreenPreview() {
    val statePreview = PersonListState(
        persons = List (10) { provideRandomPerson() }
    )
    Si2GAssistantTheme {
        PersonsScreen(
            state = statePreview
        )
        {}
    }
}