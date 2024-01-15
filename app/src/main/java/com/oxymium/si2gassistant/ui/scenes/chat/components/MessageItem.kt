package com.oxymium.si2gassistant.ui.scenes.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.currentUser
import com.oxymium.si2gassistant.domain.entities.Message
import com.oxymium.si2gassistant.ui.scenes.chat.ChatEvent
import com.oxymium.si2gassistant.ui.theme.Black
import com.oxymium.si2gassistant.ui.theme.Chat1
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White
import com.oxymium.si2gassistant.utils.DateUtils
import com.oxymium.si2gassistant.utils.TimeMode

@Composable
fun MessageItem(
    message: Message,
    event: (ChatEvent) -> Unit
) {

    val isCurrentUser = (message.submittedBy == currentUser?.mail)

    Column(
        modifier = Modifier
            .padding(
                start = if (isCurrentUser) 32.dp else 0.dp,
                end = if (isCurrentUser) 0.dp else 32.dp
            )
            .fillMaxWidth()
            .background(
                color = if (isCurrentUser) Chat1 else Neutral,
                shape = RoundedCornerShape(8.dp)
            )
    ) {

        // Submitted by
        Row(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Icon(
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    painter = painterResource(id = R.drawable.ic_blur),
                    contentDescription = "sender",
                    tint = White
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    text = "${message.submittedBy}",
                    color = White
                )

                if (isCurrentUser) {

                    Icon(
                        modifier = Modifier
                            .clickable { event.invoke(ChatEvent.OnDeleteMessageButtonClick(message)) }
                            .align(Alignment.TopEnd),
                        painter = painterResource(id = R.drawable.ic_close_thick),
                        contentDescription = "sender",
                        tint = White
                    )

                }

            }

        }

        // Submitted date
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .fillMaxWidth()
        ) {

            val submittedDate = DateUtils.convertMillisToDate(message.submittedDate)
            val submittedTime =
                DateUtils.convertMillisToTime(message.submittedDate, TimeMode.SECONDS)
            Text(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                text = "$submittedDate ≡ $submittedTime",
                color = White,
                textAlign = TextAlign.End
            )

        }

        // Message content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(
                    color = White,
                    shape = RoundedCornerShape(8.dp)
                )

        ) {

            Text(
                modifier = Modifier
                    .padding(4.dp),
                text = "${message.content}",
                color = Black
            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun MessageItemPreview() {
    Si2GAssistantTheme {
        val previewMessage = Message("", 1000L, "test@gmail.net", "Lorem Ipusm")
        MessageItem(
            message = previewMessage
        ) {}
    }
}