package com.alexbralves.android.rxjava.core

/** Representa o estado de uma operação assíncrona. */
sealed class AsyncResult<out T> {
    data object Loading : AsyncResult<Nothing>()
    data class Success<T>(val value: T) : AsyncResult<T>()
    data class Failure(val error: ErrorResponse) : AsyncResult<Nothing>()
}

