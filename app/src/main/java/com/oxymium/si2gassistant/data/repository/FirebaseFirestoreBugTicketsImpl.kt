package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseFirestoreBugTicketsImpl(val firebaseFirestore: FirebaseFirestore):
    BugTicketRepository {


        //

         override fun getAllBugTickets(): Flow<Result<List<BugTicket>>> = callbackFlow {
             trySend(Result.Loading())
             val bugTicketsCollection = firebaseFirestore.collection(FirebaseFirestoreCollections.BUG_TICKETS)
             val listener = bugTicketsCollection
                 .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    trySend(Result.Failed(exception.message.toString())).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val bugTickets: List<BugTicket> =
                        querySnapshot.documents.mapNotNull { document ->
                            val bugTicket = document.toObject(BugTicket::class.java)
                            bugTicket?.id = document.id // assign the reference of the document for the ID
                            bugTicket
                        }
                    trySend(Result.Success(bugTickets)).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    override fun getBugTicketsByUser(mail: String): Flow<List<BugTicket>> = callbackFlow {
        val personsCollection = firebaseFirestore
            .collection(FirebaseFirestoreCollections.BUG_TICKETS)
            .whereEqualTo("submittedBy", mail)
        val listener = personsCollection
            .addSnapshotListener { querySnapshot, exception ->
                print(exception?.message.toString())
                if (exception != null) {
                    trySend(emptyList()).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val bugTicketList: List<BugTicket> =
                        querySnapshot.documents.mapNotNull { document ->
                            val bugTicket = document.toObject(BugTicket::class.java)
                            bugTicket?.id = document.id // assign the reference of the document for the ID
                            bugTicket
                        }
                    trySend(bugTicketList).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun updateBugTicket(bugTicket: BugTicket): Deferred<String?> {
        val result = CompletableDeferred<String?>()
        firebaseFirestore
            .collection(FirebaseFirestoreCollections.BUG_TICKETS)
            .document(bugTicket.id ?: "")
            .update("resolved", true)
            .addOnSuccessListener {
                result.complete("SUCCESS")
            }
            .addOnFailureListener {
                result.complete("FAILURE")
            }
        return result
    }

    override suspend fun createBugTicket(bugTicket: BugTicket): Deferred<String?> {
        val result = CompletableDeferred<String?>()
        firebaseFirestore
            .collection(FirebaseFirestoreCollections.BUG_TICKETS)
            .document()// auto-generates ID
            .set(bugTicket)
            .addOnSuccessListener {
                result.complete("SUCCESS")
            }
            .addOnFailureListener {
                result.complete("FAILURE")
            }
        return result
    }
}