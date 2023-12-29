package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.BugTicket
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface BugTicketRepository {
    fun getAllBugTickets(): Flow<List<BugTicket>>

    fun getBugTicketsByUser(mail: String): Flow<List<BugTicket>>

    suspend fun updateBugTicket(bugTicket: BugTicket): Deferred<String?>
    suspend fun createBugTicket(bugTicket: BugTicket): Deferred<String?>

}