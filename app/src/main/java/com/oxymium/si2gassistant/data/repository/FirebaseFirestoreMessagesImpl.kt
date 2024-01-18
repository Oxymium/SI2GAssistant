package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreCollections
import com.oxymium.si2gassistant.domain.entities.Message
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.MessageRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseFirestoreMessagesImpl(
    val firebaseFirestore: FirebaseFirestore
): MessageRepository {

    // GET: ALL
    override fun getAllMessages(): Flow<Result<List<Message>>> = callbackFlow {
        // Loading
        trySend(Result.Loading())

        val messagesCollection = firebaseFirestore.collection(FirebaseFirestoreCollections.MESSAGES)

        val listener = messagesCollection
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    // Failure
                    trySend(Result.Failed(exception.message.toString())).isFailure
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val messages: List<Message> =
                        querySnapshot.documents.mapNotNull { document ->
                            val message = document.toObject(Message::class.java)
                            message?.id = document.id // assign the reference of the document for the ID
                            message
                        }
                    // Success
                    trySend(Result.Success(messages)).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    // SUBMIT: MESSAGE
    override suspend fun submitMessage(message: Message) = suspendCancellableCoroutine { continuation ->
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.MESSAGES)
                .add(message)
                .addOnSuccessListener {
                    // Success
                    continuation.resume(Unit)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
    }

    // DELETE: MESSAGE
    override suspend fun deleteMessage(message: Message) = suspendCancellableCoroutine { continuation ->
        firebaseFirestore
            .collection(FirebaseFirestoreCollections.MESSAGES)
            .document(message.id ?: "")
            .delete()
            .addOnSuccessListener {
                // Success
                continuation.resume(Unit)
            }
            .addOnFailureListener {
                // Failure
                continuation.resumeWithException(it)
            }
    }

}