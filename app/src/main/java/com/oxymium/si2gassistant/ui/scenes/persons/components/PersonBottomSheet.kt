package com.oxymium.si2gassistant.ui.scenes.persons.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.oxymium.si2gassistant.domain.entities.mock.ALL_MODULES
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonEvent
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonState
import com.oxymium.si2gassistant.ui.theme.Neutral

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonBottomSheet(
    state: SubmitPersonState,
    event: (SubmitPersonEvent) -> Unit
) {

    if (state.selectedPerson != null) {

        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(0.95f),
            containerColor = Neutral,
            onDismissRequest = { event.invoke(SubmitPersonEvent.DismissPersonDetailsSheet) }
        ) {

            Text(
                text = "${state.selectedPerson.firstname}",
                color = Color.White
            )

            Text(
                text = "${state.selectedPerson.lastname}",
                color = Color.White
            )

            Text(
                text = "${state.selectedPerson.academy}",
                color = Color.White
                )

            val validatedModules: List<Int> = state.selectedPerson.validatedModules
                ?.split(", ")
                ?.map { it.toInt() }
                ?: emptyList()

            // MODULES
            for (i in ALL_MODULES) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Text(
                        text = i.id.toString(),
                        color = Color.White
                    )

                    Switch(
                        checked = i.id?.toInt() in validatedModules,
                        onCheckedChange = { isChecked ->

                        }
                    )
                }

            }

        }
    }

}