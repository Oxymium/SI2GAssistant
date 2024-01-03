package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.Suggestion
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface SuggestionRepository {

    fun getAllSuggestions(): Flow<Result<List<Suggestion>>>
    suspend fun submitSuggestion(suggestion: Suggestion): Flow<Result<Boolean>>

}