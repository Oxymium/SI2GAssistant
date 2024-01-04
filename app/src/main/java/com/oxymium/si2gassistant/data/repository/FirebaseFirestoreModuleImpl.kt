package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.Module
import com.oxymium.si2gassistant.domain.repository.ModuleRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseFirestoreModuleImpl(val firebaseFirestore: FirebaseFirestore): ModuleRepository {

    // GET: ALL
    override fun getAllModules(): Flow<Result<List<Module>>> = callbackFlow {
        trySend(Result.Loading())
        val moduleCollection = firebaseFirestore.collection(FirebaseFirestoreCollections.MODULES)
        val listener = moduleCollection
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    trySend(Result.Failed(exception.message.toString())).isFailure
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val modules: List<Module> =
                        querySnapshot.documents.mapNotNull { it.toObject(Module::class.java) }
                    trySend(Result.Success(modules)).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }
}