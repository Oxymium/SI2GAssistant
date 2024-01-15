package com.oxymium.si2gassistant.ui.scenes.suggestions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.entities.Suggestion
import com.oxymium.si2gassistant.domain.mock.provideRandomSuggestion
import com.oxymium.si2gassistant.ui.theme.Black
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.Suggestions1
import com.oxymium.si2gassistant.ui.theme.Suggestions2
import com.oxymium.si2gassistant.ui.theme.Suggestions3
import com.oxymium.si2gassistant.ui.theme.Suggestions4
import com.oxymium.si2gassistant.ui.theme.White
import com.oxymium.si2gassistant.utils.DateUtils
import com.oxymium.si2gassistant.utils.TimeMode

@Composable
fun SuggestionItem(
    index: Int,
    suggestion: Suggestion
) {

    val colors = listOf(Suggestions1, Suggestions2, Suggestions3, Suggestions4)
    val colorIndex = index % colors.size // modulo
    val backgroundColor = colors[colorIndex] // colorIndex to get color from list

    Column(
        modifier = Modifier
            .padding(
                start = if (index % 2 == 0) 32.dp else 0.dp,
                end = if (index % 2 == 0) 0.dp else 32.dp
            ) // modify margin based on even or uneven Index to alternate
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )

    ){

        val submittedDate = DateUtils.convertMillisToDate(suggestion.submittedDate)
        val submittedTime = DateUtils.convertMillisToTime(suggestion.submittedDate, TimeMode.SECONDS)

        Row(
            modifier = Modifier
                .padding(2.dp)
            ) {

            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_blur),
                contentDescription = null,
                tint = White
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "${suggestion.submittedBy}",
                color = White,
                textAlign = TextAlign.Center
            )

        }

        Text(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            text = "$submittedDate ≡ $submittedTime",
            color = White,
            textAlign = TextAlign.End
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
            text = "Academy: ${suggestion.submittedAcademy}",
            color = White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
            text = "Subject: ${suggestion.subject}",
            color = White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )

        Box(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .background(
                    color = White,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = "${suggestion.body}",
                color = Black,
                textAlign = TextAlign.Start
            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun SuggestionItemPreview() {
    Si2GAssistantTheme {
        val previewSuggestion = provideRandomSuggestion()
        val randomIndex = (1..2).random()
        SuggestionItem(
            suggestion = previewSuggestion,
            index = randomIndex
        )
    }
}