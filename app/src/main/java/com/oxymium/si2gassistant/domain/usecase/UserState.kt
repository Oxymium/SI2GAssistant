package com.oxymium.si2gassistant.domain.usecase

import com.oxymium.si2gassistant.domain.entities.User

sealed class UserState {
    data class Success(val user: User): UserState()
    data class Error(val exception: Exception): UserState()
    data object Loading: UserState()

}