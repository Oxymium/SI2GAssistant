package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.repository.AcademyRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseFirestoreAcademiesImpl(val firebaseFirestore: FirebaseFirestore): AcademyRepository {
    override fun getAllAcademies(): Flow<List<Academy>> = callbackFlow {
        val academiesCollection = firebaseFirestore.collection(FirebaseFirestoreCollections.ACADEMIES)
        val listener = academiesCollection
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    trySend(emptyList()).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val academyList: List<Academy> =
                        querySnapshot.documents.mapNotNull { it.toObject(Academy::class.java) }
                    trySend(academyList).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }
}