package com.alexbralves.android.coroutines.core

sealed interface AsyncResult<out T> {
    data object Loading : AsyncResult<Nothing>
    data class Success<T>(val value: T) : AsyncResult<T>
    data class Failure(val error: ErrorResponse) : AsyncResult<Nothing>
}

