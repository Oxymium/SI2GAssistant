package com.oxymium.si2gassistant.ui.scenes.academylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.usecase.AcademyListEvent
import com.oxymium.si2gassistant.ui.scenes.academylist.components.AcademyList
import com.oxymium.si2gassistant.ui.scenes.academylist.components.AcademyListState

@Composable
fun AcademiesScreen(
    state: AcademyListState,
    event: (AcademyListEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column {

            Row{

                Icon(
                    modifier = Modifier
                        .clickable { event.invoke(AcademyListEvent.OnAlphabeticallyFilterButtonClick)  },
                    painter = painterResource(id = R.drawable.ic_sort_alphabetical_variant),
                    contentDescription = null
                )

                Icon(
                    modifier = Modifier
                        .clickable { event.invoke(AcademyListEvent.OnNumericallyFilterButtonClick)  },
                    painter = painterResource(id = R.drawable.ic_sort_numeric_variant),
                    contentDescription = null
                )
            }

            AcademyList(
                state = state,
                event = event
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AcademiesScreenPreview() {
    val statePreview = AcademyListState()
    AcademiesScreen(
        state = statePreview
    ) {}
}