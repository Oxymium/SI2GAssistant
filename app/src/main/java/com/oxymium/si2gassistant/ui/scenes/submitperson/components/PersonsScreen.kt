package com.oxymium.si2gassistant.ui.scenes.submitperson.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.ui.scenes.persons.components.PersonItemTest
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonEvent
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonState
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun PersonsScreen_Nu(
    state: SubmitPersonState,
    event: (SubmitPersonEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(state.persons) { index, person ->

                PersonItemTest(
                    index = index,
                    person = person,
                    event = event
                )

            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PersonsScreen_NuPreview(){
    Si2GAssistantTheme {
        val previewState = SubmitPersonState(
            persons = listOf(
                Person("", "", "Jean", "Michel", "1, 5, 7", "Rouen", "","", ""),
                Person("", "", "Marie", "Paul", "2, 5, 6, 9", "Grenoble", "", "", "")
            )
        )
        PersonsScreen_Nu(
            state = previewState
        ) {}
    }
}