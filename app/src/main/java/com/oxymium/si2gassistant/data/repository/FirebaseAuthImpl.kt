package com.oxymium.si2gassistant.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.oxymium.si2gassistant.domain.repository.AuthRepository
import com.oxymium.si2gassistant.domain.usecase.FirebaseAuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class FirebaseAuthImpl(val firebaseAuth: FirebaseAuth): AuthRepository {
    override suspend fun getFirebaseAuthWithCredentials(email: String, password: String): Flow<FirebaseAuthState?> = flow {

        try {
            emit(FirebaseAuthState.Loading) // initial state

            val authResult = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            emit(FirebaseAuthState.Success(authResult.user))

        } catch (e: Exception) {
            emit(FirebaseAuthState.Failure(e))
        }

    }.flowOn(Dispatchers.IO)

}