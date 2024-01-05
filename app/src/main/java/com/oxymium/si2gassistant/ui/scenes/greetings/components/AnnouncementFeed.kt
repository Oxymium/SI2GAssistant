package com.oxymium.si2gassistant.ui.scenes.greetings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.announcementsCycleDuration
import com.oxymium.si2gassistant.domain.entities.Announcement
import com.oxymium.si2gassistant.domain.states.GreetingsState
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import kotlinx.coroutines.delay

@Composable
fun AnnouncementFeed(
    state: GreetingsState
) {

    Column(
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
    ) {

        var currentIndex by remember { mutableIntStateOf(0) }
        val currentAnnouncement = state.announcements.sortedBy { it.submittedDate }.reversed().getOrNull(currentIndex)

        LaunchedEffect(currentAnnouncement) {
            while (true) {
                // Delay timer to load next announcement
                delay(announcementsCycleDuration)

                // Move to the next index
                if (state.announcements.isNotEmpty()) { // prevents divide by 0 error if list is empty
                    currentIndex = (currentIndex + 1) % state.announcements.size
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {

                AnnouncementItem(
                    index = currentIndex,
                    announcement = currentAnnouncement ?: Announcement()
                )

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AnnouncementFeedPreview() {
    Si2GAssistantTheme {
        val previewState = GreetingsState(
            announcements = emptyList()
        )
        AnnouncementFeed(
            state = previewState
        )
    }
}