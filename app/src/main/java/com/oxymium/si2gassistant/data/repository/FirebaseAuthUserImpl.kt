package com.oxymium.si2gassistant.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.oxymium.si2gassistant.domain.usecase.FirebaseUserState
import com.oxymium.si2gassistant.domain.repository.FirebaseUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class FirebaseAuthUserImpl(val firebaseAuth: FirebaseAuth): FirebaseUserRepository {
    override suspend fun getFirebaseUserWithCredentials(email: String, password: String): Flow<FirebaseUserState?> = flow {

        try {
            emit(FirebaseUserState.Loading) // initial state

            val authResult = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            emit(FirebaseUserState.Success(authResult.user))

        } catch (e: Exception) {
            emit(FirebaseUserState.Failure(e))
        }

    }.flowOn(Dispatchers.IO)

}