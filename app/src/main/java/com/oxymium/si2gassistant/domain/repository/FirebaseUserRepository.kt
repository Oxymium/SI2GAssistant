package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.usecase.FirebaseUserState
import kotlinx.coroutines.flow.Flow

interface FirebaseUserRepository {
    suspend fun getFirebaseUserWithCredentials(email: String, password: String): Flow<FirebaseUserState?>

}