package com.oxymium.si2gassistant.ui.scenes.greetings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.domain.entities.Announcement
import com.oxymium.si2gassistant.ui.theme.Announcement1
import com.oxymium.si2gassistant.ui.theme.Announcement2
import com.oxymium.si2gassistant.ui.theme.Announcement3
import com.oxymium.si2gassistant.ui.theme.Announcement4
import com.oxymium.si2gassistant.ui.theme.Announcement5
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White
import com.oxymium.si2gassistant.utils.DateUtils
import com.oxymium.si2gassistant.utils.TimeMode

@Composable
fun AnnouncementItem(
    index: Int,
    announcement: Announcement
) {

    val colors = listOf(Announcement1, Announcement2, Announcement3, Announcement4, Announcement5)
    val backgroundColor =
        colors[index % colors.size] // modulo to cycle through the colors based on index

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentSize()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {

        val submittedDateConvertedToDate =
            DateUtils.convertMillisToDate(announcement.submittedDate)
        val submittedDateConvertedToTime =
            DateUtils.convertMillisToTime(announcement.submittedDate, TimeMode.MINUTES)


        // TITLE
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp
                ),
            text = "▣ $submittedDateConvertedToDate ≡ $submittedDateConvertedToTime",
            color = White,
            textAlign = TextAlign.End,
            minLines = 1,
            maxLines = 1
        )

        // TITLE
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp
                ),
            text = "▣ ${announcement.title}",
            color = White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            minLines = 1,
            maxLines = 1
        )

        // CONTENT
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = "▣ ${announcement.content}",
            color = White,
            textAlign = TextAlign.Start,
            minLines = 1,
            maxLines = 3
        )

    }
}

@Preview(showBackground = true)
@Composable
fun AnnouncementItemPreview() {
    Si2GAssistantTheme {
        val previewItem = Announcement(
            "",
            "Reminder",
            "Video meeting on 12/01/2024",
            0L
        )
        AnnouncementItem(
            index = (1..5).random(),
            announcement = previewItem
        )
    }
}


