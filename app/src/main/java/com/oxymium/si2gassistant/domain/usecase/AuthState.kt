package com.oxymium.si2gassistant.domain.usecase

import com.google.firebase.auth.FirebaseUser

sealed class FirebaseUserState {
    data object Loading : FirebaseUserState()
    data class Success(val firebaseUser: FirebaseUser?) : FirebaseUserState()
    data class Failure(val exception: Exception) : FirebaseUserState()
}