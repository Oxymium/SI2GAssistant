package com.oxymium.si2gassistant.ui.scenes.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.ui.scenes.chat.ChatEvent
import com.oxymium.si2gassistant.ui.scenes.chat.ChatState
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun MessageList(
    state: ChatState,
    event: (ChatEvent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
           
            items(state.messages) {message ->
                
                MessageItem(
                    message = message,
                    event = event
                )
                
            }
            
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MessageListPreview() {
    Si2GAssistantTheme {
        val previewState = ChatState(emptyList())
        MessageList(
            state = previewState
        ) {}
    }
}