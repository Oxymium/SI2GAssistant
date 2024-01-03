package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.Result
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface BugTicketRepository {

    // GET: ALL
    fun getAllBugTickets(): Flow<Result<List<BugTicket>>>

    // GET: ALL BY USER
    fun getBugTicketsByUser(mail: String): Flow<Result<List<BugTicket>>>

    // SUBMIT: BUG TICKET
    suspend fun submitBugTicket(bugTicket: BugTicket): Flow<Result<Boolean>>

    // UPDATE: BUG TICKET
    suspend fun updateBugTicket(bugTicket: BugTicket): Flow<Result<Boolean>>

}