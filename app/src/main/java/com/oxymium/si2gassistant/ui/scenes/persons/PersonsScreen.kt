package com.oxymium.si2gassistant.ui.scenes.persons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.domain.entities.Person
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

        PersonList(
            state = state,
            event = event
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PersonsScreenPreview() {
    val personListPreview = listOf(
        Person("10", "","110000", "", "", "", "", "", ""),
        Person("10","","110000", "", "", "", "", "", ""),
        Person("10","","110000", "", "", "", "", "", "")
        )
    val statePreview = PersonListState(personList = personListPreview)
    Si2GAssistantTheme {
        PersonsScreen(
            state = statePreview ){}
    }
}