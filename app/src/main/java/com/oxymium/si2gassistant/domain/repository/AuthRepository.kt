package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.usecase.FirebaseAuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun getFirebaseAuthWithCredentials(email: String, password: String): Flow<FirebaseAuthState?>

}