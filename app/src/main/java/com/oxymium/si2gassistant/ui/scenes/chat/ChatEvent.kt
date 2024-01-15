package com.oxymium.si2gassistant.ui.scenes.chat

import com.oxymium.si2gassistant.domain.entities.Message

sealed interface ChatEvent {
    data class OnContentChange(val content: String): ChatEvent
    data object OnSubmitMessageButtonClick: ChatEvent
    data class OnDeleteMessageButtonClick(val message: Message): ChatEvent
}