package com.oxymium.si2gassistant.domain.validators

import com.oxymium.si2gassistant.domain.entities.Message

object MessageValidator {

    fun validateBugTicket(message: Message): MessageValidationData {
        return MessageValidationData(
            messageContentError = message.content.isNullOrEmpty()
        )
    }
}

data class MessageValidationData(
    val messageContentError: Boolean
) {
    fun hasErrors(): Boolean {
        return messageContentError
    }
}