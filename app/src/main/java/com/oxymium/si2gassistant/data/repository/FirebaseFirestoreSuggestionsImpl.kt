package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.Suggestion
import com.oxymium.si2gassistant.domain.entities.pushError
import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class FirebaseFirestoreSuggestionsImpl(val firebaseFirestore: FirebaseFirestore): SuggestionRepository {

    // GET: ALL
    override fun getAllSuggestions(): Flow<Result<List<Suggestion>>> = callbackFlow {

        trySend(Result.Loading()).isSuccess // Emit Loading state

        val personsCollection = firebaseFirestore
            .collection(FirebaseFirestoreCollections.SUGGESTIONS)

        val listener = personsCollection.addSnapshotListener { querySnapshot, exception ->
            if (exception != null) {
                trySend(Result.Failed(exception.message.toString())).isSuccess
                return@addSnapshotListener
            }

            if (querySnapshot != null) {
                val suggestionList: List<Suggestion> =
                    querySnapshot.documents.mapNotNull { document ->
                        val suggestion = document.toObject(Suggestion::class.java)
                        suggestion?.id = document.id // assign the reference of the document for the ID
                        suggestion
                    }

                trySend(Result.Success(suggestionList)).isSuccess
            }
        }

        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    // PUSH: SUGGESTION
    override suspend fun submitSuggestion(suggestion: Suggestion): Flow<Result<Boolean>> = flow {
        // Loading
        emit(Result.Loading())

        val result = CompletableDeferred<Boolean>()

        try {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.SUGGESTIONS)
                .document()// auto-generates ID
                .set(suggestion)
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