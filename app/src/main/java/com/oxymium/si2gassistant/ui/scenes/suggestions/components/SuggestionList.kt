package com.oxymium.si2gassistant.ui.scenes.suggestions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomSuggestion
import com.oxymium.si2gassistant.ui.scenes.suggestions.SuggestionsState
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun SuggestionList(
    state: SuggestionsState
) {

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(state.suggestions) { index, suggestion ->
                SuggestionItem(
                    index = index,
                    suggestion = suggestion,
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SuggestionListPreview() {
    Si2GAssistantTheme {
        val previewList = List(10) { provideRandomSuggestion() }
        val previewState = SuggestionsState(suggestions = previewList)
        SuggestionList(
            state = previewState
        )
    }
}