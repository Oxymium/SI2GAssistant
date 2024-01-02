package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.User
import com.oxymium.si2gassistant.domain.usecase.UserState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getAllUsers(): Flow<Result<List<User>>>

    fun getUserByUid(uid: String): Flow<UserState>

}