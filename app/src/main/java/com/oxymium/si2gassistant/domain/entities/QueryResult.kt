package com.oxymium.si2gassistant.domain.entities

sealed class QueryResult<out T> {
    data class Loading<out T>(val data: T? = null): QueryResult<T>()
    data class Success<out T>(val data: T): QueryResult<T>()
    data class Failed<out T>(val errorMessage: String, val data: T? = null): QueryResult<T>()
}