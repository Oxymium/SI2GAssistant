package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreCollections
import com.oxymium.si2gassistant.domain.entities.Message
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.pushError
import com.oxymium.si2gassistant.domain.repository.MessageRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

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
    override fun submitMessage(message: Message): Flow<Result<Boolean>> = flow {
        // Loading
        emit(Result.Loading())

        val result = CompletableDeferred<Boolean>()

        try {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.MESSAGES)
                .document()// auto-generates ID
                .set(message)
                .addOnSuccessListener {
                    // Success
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