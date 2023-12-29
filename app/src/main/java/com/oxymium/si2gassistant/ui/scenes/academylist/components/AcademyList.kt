package com.oxymium.si2gassistant.ui.scenes.academylist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.entities.mock.ALL_ACADEMIES
import com.oxymium.si2gassistant.domain.usecase.AcademyListEvent
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun AcademyList(
    state: AcademyListState,
    event: (AcademyListEvent) -> Unit
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
            items(state.academyList) { academy ->
                AcademyItem(
                    academy = academy,
                    event = event
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AcademyListPreview() {
    val statePreview = AcademyListState(ALL_ACADEMIES.take(5))
    Si2GAssistantTheme {
        AcademyList(state = statePreview) { }
    }
}