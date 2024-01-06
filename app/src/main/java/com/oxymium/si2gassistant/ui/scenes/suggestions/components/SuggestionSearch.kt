package com.oxymium.si2gassistant.ui.scenes.suggestions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.ui.scenes.suggestions.SuggestionsEvent
import com.oxymium.si2gassistant.domain.states.SuggestionsState
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.TextAccent
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun SuggestionSearch(
    state: SuggestionsState,
    event: (SuggestionsEvent) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = Neutral,
                shape = RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            )
    ) {

        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            var search by remember { mutableStateOf("") }

            Box(
                modifier = Modifier
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = search,
                    onValueChange = {
                        search = it.take(25)
                        event.invoke(SuggestionsEvent.OnSearchTextChange(search))
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = TextAccent,
                        cursorColor = White,
                        focusedBorderColor = TextAccent,
                        unfocusedBorderColor = White
                    ),
                    label = {
                        androidx.compose.material.Text(
                            text = when(state.suggestions.size) {
                                0 -> "Quick search: 0 results"
                                1 -> "Quick search: 1 result"
                                else -> "Quick search: ${state.suggestions.size} results"
                            },
                            color = Color.White
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email
                    ),
                    maxLines = 1
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuggestionSearchPreview() {
    Si2GAssistantTheme {
        val previewState = SuggestionsState()
        SuggestionSearch(
            state = previewState
        ) { }
    }
}