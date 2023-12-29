package com.oxymium.si2gassistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.domain.entities.User
import com.oxymium.si2gassistant.domain.repository.UserRepository
import com.oxymium.si2gassistant.domain.usecase.UserState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseFirestoreUserImpl(
    val firebaseFirestore: FirebaseFirestore,
): UserRepository {

    override fun getAllUsers(): Flow<List<User>> = callbackFlow {
        val personsCollection = firebaseFirestore
            .collection(FirebaseFirestoreCollections.USERS)
        val listener = personsCollection
            .addSnapshotListener { querySnapshot, exception ->
                print(exception?.message.toString())
                if (exception != null) {
                    trySend(emptyList()).isSuccess
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val userList: List<User> =
                        querySnapshot.documents.mapNotNull { document ->
                            val user = document.toObject(User::class.java)
                            user
                        }
                    trySend(userList).isSuccess
                }
            }
        // The callbackFlow will automatically close the listener when the flow is cancelled
        awaitClose {
            listener.remove()
        }
    }

    override fun getUserByUid(uid: String): Flow<UserState> = callbackFlow {
        trySend(UserState.Loading) // loading first
        val usersCollection = firebaseFirestore.collection(FirebaseFirestoreCollections.USERS)
        val listener = usersCollection
            .document(uid)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    trySend(UserState.Error(exception)).isSuccess
                    return@addSnapshotListener
                }

                val user: User? = querySnapshot?.toObject(User::class.java)
                if (user != null) {
                    trySend(UserState.Success(user)).isSuccess // Success
                } else {
                    trySend(UserState.Error(Exception("User not found"))) // failure if User doesn't exist
                }
            }
        awaitClose {
            listener.remove()
        }
    }

}