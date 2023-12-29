package com.oxymium.si2gassistant.ui.scenes.academylist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.entities.mock.ALL_ACADEMIES
import com.oxymium.si2gassistant.domain.usecase.AcademyListEvent
import com.oxymium.si2gassistant.ui.theme.Blue
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun AcademyItem(
    academy: Academy,
    event: (AcademyListEvent) -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .wrapContentSize()
            .background(color = Blue, shape = MaterialTheme.shapes.medium)
            .clickable {
                event.invoke(AcademyListEvent.SelectAcademy(academy))
            }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text(
                text = academy.shortTitle,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AcademyItemPreview() {
    Si2GAssistantTheme {
        val academyPreview = ALL_ACADEMIES.random()
        AcademyItem(academyPreview) {
        }
    }
}