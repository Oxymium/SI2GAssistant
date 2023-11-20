package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseFirestoreBugTicketsImpl(val firebaseFirestore: FirebaseFirestore):
    BugTicketRepository {
    override fun getAllBugTickets(): Flow<List<BugTicket>> = callbackFlow {
        val bugTicketsCollection = firebaseFirestore.collection(FirebaseFirestoreCollections.BUG_TICKETS)
        val listener = bugTicketsCollection
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    trySend(emptyList()).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val bugTicketList: List<BugTicket> =
                        querySnapshot.documents.mapNotNull { it.toObject(BugTicket::class.java) }
                    trySend(bugTicketList).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun createBugTicket(bugTicket: BugTicket): Deferred<String?> {
        val result = CompletableDeferred<String?>()
        firebaseFirestore
            .collection(FirebaseFirestoreCollections.BUG_TICKETS)
            .document()// auto-generates ID
            .set(bugTicket)
            .addOnSuccessListener {
                println("CREATEBUGTICKET: IMPL: SUCCESS")
                result.complete("SUCCESS")
            }
            .addOnFailureListener {
                println("CREATEBUGTICKET: IMPL: FAILED")
                result.complete("FAILURE")
            }
        return result
    }
}