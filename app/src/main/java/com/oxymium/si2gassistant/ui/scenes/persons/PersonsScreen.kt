package com.oxymium.si2gassistant.ui.scenes.persons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.domain.mock.provideRandomPerson
import com.oxymium.si2gassistant.ui.scenes.animations.LoadingAnimation
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonList
import com.oxymium.si2gassistant.domain.states.PersonListState
import com.oxymium.si2gassistant.ui.scenes.animations.NothingAnimation
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonSearch
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun PersonsScreen(
    state: PersonListState,
    event: (PersonsEvent) -> Unit
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

            if (state.persons.isEmpty()) {

                NothingAnimation()

            } else {

                PersonList(
                    state = state
                )

            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun PersonsScreenPreview() {
    val statePreview = PersonListState(
        persons = List (5) { provideRandomPerson() }
    )
    Si2GAssistantTheme {
        PersonsScreen(
            state = statePreview
        )
        {}
    }
}