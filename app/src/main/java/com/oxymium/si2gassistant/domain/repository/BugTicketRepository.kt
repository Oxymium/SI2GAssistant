package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.BugTicket
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface BugTicketRepository {
    fun getAllBugTickets(): Flow<List<BugTicket>>

    suspend fun createBugTicket(bugTicket: BugTicket): Deferred<String?>

}