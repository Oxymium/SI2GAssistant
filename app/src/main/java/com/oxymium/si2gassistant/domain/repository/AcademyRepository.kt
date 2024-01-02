package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.entities.Result
import kotlinx.coroutines.flow.Flow

interface AcademyRepository {

    fun getAllAcademies(): Flow<Result<List<Academy>>>

}