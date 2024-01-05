package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreCollections
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.AcademyRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseFirestoreAcademiesImpl(val firebaseFirestore: FirebaseFirestore): AcademyRepository {

    // GET: ALL
    override fun getAllAcademies(): Flow<Result<List<Academy>>> = callbackFlow {
        trySend(Result.Loading())
        val academiesCollection = firebaseFirestore.collection(FirebaseFirestoreCollections.ACADEMIES)
        val listener = academiesCollection
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    trySend(Result.Failed(exception.message.toString())).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val academies: List<Academy> =
                        querySnapshot.documents.mapNotNull { it.toObject(Academy::class.java) }
                    trySend(Result.Success(academies)).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }
}