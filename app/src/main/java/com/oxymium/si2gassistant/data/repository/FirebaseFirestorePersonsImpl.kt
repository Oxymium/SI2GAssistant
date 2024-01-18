package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreCollections
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreFields
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseFirestorePersonsImpl(val firebaseFirestore: FirebaseFirestore): PersonRepository {

    // GET: ALL
    override fun getAllPersons(): Flow<Result<List<Person>>> = callbackFlow {

        trySend(Result.Loading()).isSuccess // Emit Loading state

        val personsCollection = firebaseFirestore
            .collection(FirebaseFirestoreCollections.PERSONS)
        val listener = personsCollection
            .addSnapshotListener { querySnapshot, exception ->
                print(exception?.message.toString())
                if (exception != null) {
                    trySend(Result.Failed(exception.message.toString())).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val persons: List<Person> =
                        querySnapshot.documents.mapNotNull { document ->
                            val person = document.toObject(Person::class.java)
                            person?.id = document.id // assign the reference of the document for the ID
                            person
                        }
                    trySend(Result.Success(persons)).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    // GET: ALL BY USER_ID
    override fun getAllPersonsByUserId(userId: String): Flow<Result<List<Person>>> = callbackFlow {

        trySend(Result.Loading()).isSuccess // Emit Loading state

        val personsCollection = firebaseFirestore
            .collection(FirebaseFirestoreCollections.PERSONS)
            .whereEqualTo(FirebaseFirestoreFields.USER_ID, userId)
        val listener = personsCollection
            .addSnapshotListener { querySnapshot, exception ->
                print(exception?.message.toString())
                if (exception != null) {
                    trySend(Result.Failed(exception.message.toString())).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val persons: List<Person> =
                        querySnapshot.documents.mapNotNull { document ->
                            val person = document.toObject(Person::class.java)
                            person?.id = document.id // assign the reference of the document for the ID
                            person
                        }
                    trySend(Result.Success(persons)).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    // SUBMIT: PERSON
    override suspend fun submitPerson(person: Person) = suspendCancellableCoroutine { continuation ->
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


    // UPDATE: PERSON
    override suspend fun updatePerson(person: Person) = suspendCancellableCoroutine { continuation ->
        firebaseFirestore
            .collection(FirebaseFirestoreCollections.PERSONS)
            .document(person.id ?: "")
            .update(FirebaseFirestoreFields.VALIDATED_MODULES, person.validatedModules)
            .addOnSuccessListener {
                // SUCCESS
                continuation.resume(Unit)
            }
            .addOnFailureListener {
                // FAILURE
                continuation.resumeWithException(it)
            }

    }

}