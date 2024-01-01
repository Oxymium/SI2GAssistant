package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseFirestorePersonsImpl(val firebaseFirestore: FirebaseFirestore): PersonRepository {

    override fun getAllPersons(): Flow<List<Person>> = callbackFlow {
        val personsCollection = firebaseFirestore
            .collection(FirebaseFirestoreCollections.PERSONS)
        val listener = personsCollection
            .addSnapshotListener { querySnapshot, exception ->
                print(exception?.message.toString())
                if (exception != null) {
                    trySend(emptyList()).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val personList: List<Person> =
                        querySnapshot.documents.mapNotNull { document ->
                            val person = document.toObject(Person::class.java)
                            person?.id = document.id // assign the reference of the document for the ID
                            person
                        }
                    trySend(personList).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    override fun getAllPersonsByAcademyId(userId: String): Flow<List<Person>> = callbackFlow {
        val personsCollection = firebaseFirestore
            .collection(FirebaseFirestoreCollections.PERSONS)
            .whereEqualTo(FirebaseFirestoreFields.USER_ID, userId)
        val listener = personsCollection
            .addSnapshotListener { querySnapshot, exception ->
                print(exception?.message.toString())
                if (exception != null) {
                    trySend(emptyList()).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val personList: List<Person> =
                        querySnapshot.documents.mapNotNull { document ->
                            val person = document.toObject(Person::class.java)
                            person?.id = document.id // assign the reference of the document for the ID
                            person
                        }
                    trySend(personList).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    override fun getAllPersonsByUserId(userId: String): Flow<List<Person>> = callbackFlow {
        val personsCollection = firebaseFirestore
            .collection(FirebaseFirestoreCollections.PERSONS)
            .whereEqualTo("userId", userId)
        val listener = personsCollection
            .addSnapshotListener { querySnapshot, exception ->
                print(exception?.message.toString())
                if (exception != null) {
                    trySend(emptyList()).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val personList: List<Person> =
                        querySnapshot.documents.mapNotNull { document ->
                            val person = document.toObject(Person::class.java)
                            person?.id = document.id // assign the reference of the document for the ID
                            person
                        }
                    trySend(personList).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun submitPerson(person: Person): Deferred<String?> {
        val result = CompletableDeferred<String?>()
        firebaseFirestore
            .collection(FirebaseFirestoreCollections.PERSONS)
            .document()// auto-generates ID
            .set(person)
            .addOnSuccessListener {
                result.complete("SUCCESS")
            }
            .addOnFailureListener {
                result.complete("FAILURE")
            }
        return result
    }

    override suspend fun updatePersonModules(person: Person): Deferred<String?> {
        val result = CompletableDeferred<String?>()
        firebaseFirestore
            .collection(FirebaseFirestoreCollections.PERSONS)
            .document(person.id ?: "")
            .update("validatedModules", person.validatedModules)
            .addOnSuccessListener {
                result.complete("SUCCESS")
            }
            .addOnFailureListener {
                result.complete("FAILURE")
            }
        return result
    }

}