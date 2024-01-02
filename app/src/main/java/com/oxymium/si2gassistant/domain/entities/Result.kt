package com.oxymium.si2gassistant.domain.entities

sealed class Result<out T> {
    data class Loading<out T>(val data: T? = null): Result<T>()
    data class Success<out T>(val data: T): Result<T>()
    data class Failed<out T>(val errorMessage: String, val data: T? = null): Result<T>()
}

const val pushError = "Failed to update"