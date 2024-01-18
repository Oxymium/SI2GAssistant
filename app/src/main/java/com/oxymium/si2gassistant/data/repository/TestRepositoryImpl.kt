package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.entities.Announcement
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreCollections
import com.oxymium.si2gassistant.domain.entities.Message
import com.oxymium.si2gassistant.domain.entities.Module
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Suggestion
import com.oxymium.si2gassistant.domain.repository.TestRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class TestRepositoryImpl(
    val firebaseFirestore: FirebaseFirestore
): TestRepository {

    override suspend fun submitAcademy(academy: Academy) =
        suspendCancellableCoroutine { continuation ->
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.ACADEMIES)
                .add(academy)
                .addOnSuccessListener {
                    // Success
                    continuation.resume(Unit)
                }
                .addOnFailureListener {
                    // Failure
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun submitBugTicket(bugTicket: BugTicket) =
        suspendCancellableCoroutine { continuation ->
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.BUG_TICKETS)
                .add(bugTicket)
                .addOnSuccessListener {
                    // Success
                    continuation.resume(Unit)
                }
                .addOnFailureListener {
                    // Failure
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun submitModules(module: Module) {
        suspendCancellableCoroutine { continuation ->
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.MODULES)
                .add(module)
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

    override suspend fun submitPerson(person: Person) {
        suspendCancellableCoroutine { continuation ->
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.PERSONS)
                .add(person)
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

    override suspend fun submitSuggestion(suggestion: Suggestion) {
        suspendCancellableCoroutine { continuation ->
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.SUGGESTIONS)
                .add(suggestion)
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

    override suspend fun submitAnnouncement(announcement: Announcement) {
        suspendCancellableCoroutine { continuation ->
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.ANNOUNCEMENTS)
                .add(announcement)
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

    override suspend fun submitMessage(message: Message) {
        suspendCancellableCoroutine { continuation ->
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.MESSAGES)
                .add(message)
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

}