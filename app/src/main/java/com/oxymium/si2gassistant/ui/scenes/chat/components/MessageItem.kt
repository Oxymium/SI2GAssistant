package com.oxymium.si2gassistant.ui.scenes.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.currentUser
import com.oxymium.si2gassistant.domain.entities.Message
import com.oxymium.si2gassistant.ui.theme.MenuAccent
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.NeutralLighter
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.White
import com.oxymium.si2gassistant.utils.DateUtils

@Composable
fun MessageItem(
    index: Int,
    message: Message
) {

    val backgroundColor = if (message.submittedBy == currentUser?.mail) NeutralLighter else Neutral
    Column(
        modifier = Modifier
            .padding(
                start = if (message.submittedBy == currentUser?.mail) 32.dp else 0.dp,
                end = if (message.submittedBy == currentUser?.mail) 0.dp else 32.dp
            )
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {

        // Submitted by
        Row(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_mail),
                contentDescription = "sender",
                tint = MenuAccent
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 2.dp),
                text = "${message.submittedBy}",
                color = White
            )

        }

        // Submitted date
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .fillMaxWidth()
        ) {

            val submittedDate = DateUtils.convertMillisToDate(message.submittedDate)
            val submittedTime = DateUtils.convertMillisToTime(message.submittedDate)
            Text(
                modifier = Modifier,
                text = "■ $submittedDate ≡ $submittedTime",
                color = White
            )

        }

        // Message content
        Text(
            modifier = Modifier
                .padding(2.dp),
            text = "${message.content}",
            color = White
        )
    }

}

@Preview(showBackground = true)
@Composable
fun MessageItemPreview() {
    Si2GAssistantTheme {
        val previewMessage = Message("", 1000L, "test@gmail.net", "Lorem Ipusm")
        MessageItem(
            index = 0,
            message = previewMessage
        )
    }
}