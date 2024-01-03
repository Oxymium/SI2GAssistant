package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.pushError
import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class FirebaseFirestoreBugTicketsImpl(val firebaseFirestore: FirebaseFirestore):
    BugTicketRepository {

    // GET: ALL
    override fun getAllBugTickets(): Flow<Result<List<BugTicket>>> = callbackFlow {
        // Loading
        trySend(Result.Loading())

        val bugTicketsCollection = firebaseFirestore.collection(FirebaseFirestoreCollections.BUG_TICKETS)

        val listener = bugTicketsCollection
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    // Failure
                    trySend(Result.Failed(exception.message.toString())).isFailure
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val bugTickets: List<BugTicket> =
                        querySnapshot.documents.mapNotNull { document ->
                            val bugTicket = document.toObject(BugTicket::class.java)
                            bugTicket?.id = document.id // assign the reference of the document for the ID
                            bugTicket
                        }
                    // Success
                    trySend(Result.Success(bugTickets)).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    // GET: ALL BY USER
    override fun getBugTicketsByUser(mail: String): Flow<Result<List<BugTicket>>> = callbackFlow {
        // Loading
        trySend(Result.Loading())

        val personsCollection = firebaseFirestore
            .collection(FirebaseFirestoreCollections.BUG_TICKETS)
            .whereEqualTo("submittedBy", mail)

        val listener = personsCollection
            .addSnapshotListener { querySnapshot, exception ->
                print(exception?.message.toString())
                if (exception != null) {
                    // Failure
                    trySend(Result.Failed(exception.message.toString())).isFailure
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

    // PUSH: BUG TICKET
    override suspend fun submitBugTicket(bugTicket: BugTicket): Flow<Result<Boolean>> = flow {
        // Loading
        emit(Result.Loading())

        val result = CompletableDeferred<Boolean?>()

        try {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.BUG_TICKETS)
                .document()// auto-generates ID
                .set(bugTicket)
                .addOnSuccessListener {
                    result.complete(true)
                }
            // Success
            emit(Result.Success(true))
        } catch (e: Exception) {
            // Failure
            emit(Result.Failed(e.message ?: pushError, false))
        }
    }

    // UPDATE: BUG TICKET
    override suspend fun updateBugTicket(bugTicket: BugTicket): Flow<Result<Boolean>> = flow {
        // Loading
        emit(Result.Loading())

        val result = CompletableDeferred<Boolean?>()

        try {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.BUG_TICKETS)
                .document(bugTicket.id ?: "")
                .update(
                    FirebaseFirestoreFields.RESOLVED, bugTicket.isResolved,
                    FirebaseFirestoreFields.RESOLVED_COMMENT, bugTicket.resolvedComment,
                    FirebaseFirestoreFields.RESOLVED_DATE, bugTicket.resolvedDate

                )
                .addOnSuccessListener {
                    result.complete(true)
                }
            // Success
            emit(Result.Success(true))
        } catch (e: Exception) {
            // Failure
            emit(Result.Failed(e.message ?: pushError, false))
        }

    }


}