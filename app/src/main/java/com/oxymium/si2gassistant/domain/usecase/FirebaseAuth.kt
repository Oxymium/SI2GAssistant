package com.oxymium.si2gassistant.domain.usecase

import com.google.firebase.auth.FirebaseUser

sealed class FirebaseAuthState {
    data object Loading : FirebaseAuthState()
    data class Success(val firebaseUser: FirebaseUser?) : FirebaseAuthState()
    data class Failure(val exception: Exception) : FirebaseAuthState()
}