package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    // GET: ALL
    fun getAllUsers(): Flow<Result<List<User>>>

    // GET: USER BY UID
    fun getUserByUid(uid: String): Flow<Result<User>>

}