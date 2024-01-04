package com.oxymium.si2gassistant.ui.scenes.suggestions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomSuggestion
import com.oxymium.si2gassistant.ui.scenes.animations.LoadingAnimation
import com.oxymium.si2gassistant.ui.scenes.suggestions.components.SuggestionList
import com.oxymium.si2gassistant.ui.scenes.suggestions.components.SuggestionSearch
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun SuggestionsScreen(
    state: SuggestionsState,
    event: (SuggestionsEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        SuggestionSearch(
            state = state,
            event = event
        )

        if (state.isSuggestionsLoading) {

            LoadingAnimation(
                modifier = Modifier
                    .fillMaxSize()
            )

        } else {

            SuggestionList(
                state = state
            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun SuggestionsScreenPreview() {
    Si2GAssistantTheme {
        val previewSuggestions = List(11) { provideRandomSuggestion() }
        val previewState = SuggestionsState(
            suggestions = previewSuggestions,
            isSuggestionsLoading = true
        )
        SuggestionsScreen(
            state = previewState
        ) {}
    }
}