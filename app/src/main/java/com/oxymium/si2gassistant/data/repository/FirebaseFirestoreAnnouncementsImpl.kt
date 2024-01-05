package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.Announcement
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreCollections
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.AnnouncementRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseFirestoreAnnouncementsImpl(val firebaseFirestore: FirebaseFirestore): AnnouncementRepository {

    // GET: ALL
    override fun getAllAnnouncements(): Flow<Result<List<Announcement>>> = callbackFlow {
        trySend(Result.Loading())
        val announcementCollection = firebaseFirestore.collection(FirebaseFirestoreCollections.ANNOUNCEMENTS)
        val listener = announcementCollection
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    trySend(Result.Failed(exception.message.toString())).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val announcements: List<Announcement> =
                        querySnapshot.documents.mapNotNull { it.toObject(Announcement::class.java) }
                    trySend(Result.Success(announcements)).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

}