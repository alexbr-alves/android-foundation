package com.alexbralves.android.coroutines.core

data class ErrorResponse(
    val message: String,
    val type: ErrorType = ErrorType.UNKNOWN,
    val code: Int? = null,
    val cause: Throwable? = null,
)

enum class ErrorType { NETWORK, TIMEOUT, VALIDATION, UNKNOWN }

