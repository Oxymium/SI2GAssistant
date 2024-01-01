package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.Person
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    fun getAllPersons(): Flow<List<Person>>

    fun getAllPersonsByAcademyId(academyId: String): Flow<List<Person>>

    fun getAllPersonsByUserId(userId: String): Flow<List<Person>>

    suspend fun submitPerson(person: Person): Deferred<String?>

    suspend fun updatePersonModules(person: Person): Deferred<String?>

}