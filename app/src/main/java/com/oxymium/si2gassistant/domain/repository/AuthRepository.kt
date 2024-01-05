package com.oxymium.si2gassistant.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.oxymium.si2gassistant.domain.entities.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun getFirebaseAuthWithCredentials(email: String, password: String): Flow<Result<FirebaseUser?>>

}