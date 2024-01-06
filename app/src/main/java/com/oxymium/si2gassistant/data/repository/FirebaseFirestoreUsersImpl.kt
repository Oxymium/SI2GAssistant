package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreCollections
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.User
import com.oxymium.si2gassistant.domain.repository.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseFirestoreUsersImpl(
    val firebaseFirestore: FirebaseFirestore,
): UserRepository {

    // GET: ALL
    override fun getAllUsers(): Flow<Result<List<User>>> = callbackFlow {
        trySend(Result.Loading())
        val personsCollection = firebaseFirestore
            .collection(FirebaseFirestoreCollections.USERS)
        val listener = personsCollection
            .addSnapshotListener { querySnapshot, exception ->
                print(exception?.message.toString())
                if (exception != null) {
                    trySend(Result.Failed(exception.message.toString())).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val users: List<User> =
                        querySnapshot.documents.mapNotNull { document ->
                            val user = document.toObject(User::class.java)
                            user
                        }
                    trySend(Result.Success(users)).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    // GET: ALL BY USER ID
    override fun getUserByUid(uid: String): Flow<Result<User>> = callbackFlow {
        trySend(Result.Loading()) // loading first
        val usersCollection = firebaseFirestore
            .collection(FirebaseFirestoreCollections.USERS)
        val listener = usersCollection
            .document(uid)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    trySend(Result.Failed(exception.message.toString()))
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val user: User? = querySnapshot.toObject(User::class.java)
                    if (user != null) {
                        user.id = uid // attach id to User
                        trySend(Result.Success(user))
                    } else {
                        trySend(Result.Failed("User not found")) // failure if User doesn't exist
                    }
                }
            }

        awaitClose {
            listener.remove()
        }
    }


}