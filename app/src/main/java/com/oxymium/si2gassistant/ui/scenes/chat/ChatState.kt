package com.oxymium.si2gassistant.ui.scenes.chat

import com.oxymium.si2gassistant.domain.entities.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isContentFieldError: Boolean = false
)