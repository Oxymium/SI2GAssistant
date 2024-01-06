package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.Login

interface DataStoreRepository {
    suspend fun putLogin(login: Login)
    suspend fun getString(key: String): String?
}