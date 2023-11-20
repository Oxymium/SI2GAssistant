package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.Academy
import kotlinx.coroutines.flow.Flow

interface AcademyRepository {

    fun getAllAcademies(): Flow<List<Academy>>

}