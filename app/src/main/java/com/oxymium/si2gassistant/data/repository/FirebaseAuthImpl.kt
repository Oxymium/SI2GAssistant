package com.oxymium.si2gassistant.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class FirebaseAuthImpl(val firebaseAuth: FirebaseAuth): AuthRepository {

    override suspend fun getFirebaseAuthWithCredentials(email: String, password: String): Flow<Result<FirebaseUser?>> = flow {

        try {
            emit(Result.Loading())

            val authResult = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            emit(Result.Success(authResult.user))
        } catch (e: Exception) {
            emit(Result.Failed(e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)

}