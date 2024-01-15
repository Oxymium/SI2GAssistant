package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.Message
import com.oxymium.si2gassistant.domain.entities.Result
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    // GET: ALL
    fun getAllMessages(): Flow<Result<List<Message>>>

    // PUSH: MESSAGE
    suspend fun submitMessage(message: Message)

    // DELETE: MESSAGE
    suspend fun deleteMessage(message: Message)
}