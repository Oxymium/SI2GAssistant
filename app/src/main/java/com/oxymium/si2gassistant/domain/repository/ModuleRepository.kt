package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.Module
import kotlinx.coroutines.flow.Flow

interface ModuleRepository {

    fun getAllModules(): Flow<List<Module>>

}