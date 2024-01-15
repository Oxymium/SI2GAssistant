package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.Person
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    // GET: ALL
    fun getAllPersons(): Flow<Result<List<Person>>>

    // GET: ALL BY USER_ID
    fun getAllPersonsByUserId(userId: String): Flow<Result<List<Person>>>

    // PUSH: PERSON
    suspend fun submitPerson(person: Person)

    // UPDATE: PERSON
    suspend fun updatePerson(person: Person)

}