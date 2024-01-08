package com.oxymium.si2gassistant.ui.scenes.chat

sealed interface ChatEvent {
    data class OnContentChange(val content: String): ChatEvent
    data object OnSubmitMessageButtonClick: ChatEvent
}