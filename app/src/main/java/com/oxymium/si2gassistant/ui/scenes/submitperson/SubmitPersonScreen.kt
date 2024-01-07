package com.oxymium.si2gassistant.ui.scenes.submitperson

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.states.SubmitPersonState
import com.oxymium.si2gassistant.ui.scenes.submitperson.components.PersonBottomSheet
import com.oxymium.si2gassistant.ui.scenes.submitperson.components.PersonsScreen_Nu
import com.oxymium.si2gassistant.ui.scenes.submitperson.components.SubmitPersonScreen
import com.oxymium.si2gassistant.ui.theme.MenuAccent
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun SubmitPersonScreen(
    state: SubmitPersonState,
    event: (SubmitPersonEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = Neutral,
                shape = RoundedCornerShape(
                    bottomStart = 25.dp,
                    bottomEnd = 25.dp
                )
            )
    ) {

        // BUTTON: PERSONS MODE
        Button(
            modifier = Modifier
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (state.personsMode) MenuAccent else White
            ),
            onClick = { event.invoke(SubmitPersonEvent.OnPersonsModeButtonClick) }
        ) {

            Icon(
                modifier = Modifier
                    .background(if (state.personsMode) MenuAccent else White)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_account),
                contentDescription = null,
                tint = if (state.personsMode) White else Neutral
            )
        }

        // BUTTON: SUBMIT PERSON MODE
        Button(
            modifier = Modifier
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (state.submitPersonMode) MenuAccent else White
            ),
            onClick = { event.invoke(SubmitPersonEvent.OnSubmitPersonModeButtonClick) }
        ) {

            Icon(
                modifier = Modifier
                    .background(if (state.submitPersonMode) MenuAccent else White)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_plus_thick),
                contentDescription = null,
                tint = if (state.submitPersonMode) White else Neutral
            )
        }

        // TITLE
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(),
            text = if (state.personsMode) "Your persons" else "Submit a person",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }

        if (state.personsMode) {
            PersonsScreen_Nu(
                state = state,
                event = event
            )
        }

        if (state.submitPersonMode) {
            SubmitPersonScreen(
                state = state,
                event = event
            )
        }

        if (state.isSelectedPersonDetailsOpen) {
            PersonBottomSheet(
                state = state,
                event = event
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun SubmitPersonScreenPreview() {
    Si2GAssistantTheme {
        val previewState = SubmitPersonState(
            submitPersonMode = true,
            personsMode = false
        )
        SubmitPersonScreen(
            state = previewState
        ) {
        }
    }
}