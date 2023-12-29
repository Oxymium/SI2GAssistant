package com.oxymium.si2gassistant.ui.scenes.persons.components

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
import com.oxymium.si2gassistant.ui.scenes.suggestions.components.SuggestionItem
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun PersonList(
    state: PersonListState,
    event: (PersonListEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(state.personList) { index, person ->

                PersonItemTest(
                    index = index,
                    person = person
                ) { }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PersonListPreview() {
    Si2GAssistantTheme {
    }
}