package com.oxymium.si2gassistant.data.repository

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.pushError
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseFirestorePersonsImpl(val firebaseFirestore: FirebaseFirestore): PersonRepository {

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
                    val personList: List<Person> =
                        querySnapshot.documents.mapNotNull { document ->
                            val person = document.toObject(Person::class.java)
                            person?.id = document.id // assign the reference of the document for the ID
                            person
                        }
                    trySend(Result.Success(personList)).isSuccess
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

    override suspend fun submitPerson(person: Person): Flow<Result<Boolean>> = flow {
        // Loading
        emit(Result.Loading())

        val result = CompletableDeferred<Boolean>()

        try {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.PERSONS)
                .document()// auto-generates ID
                .set(person)
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