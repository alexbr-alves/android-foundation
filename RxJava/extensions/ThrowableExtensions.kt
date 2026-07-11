package com.alexbralves.android.rxjava.extensions

import com.alexbralves.android.rxjava.core.ErrorResponse
import com.alexbralves.android.rxjava.core.ErrorType
import java.io.IOException
import java.net.SocketTimeoutException

fun Throwable.toErrorResponse(): ErrorResponse = when (this) {
    is SocketTimeoutException -> ErrorResponse(
        message = "A operação demorou mais que o esperado.",
        type = ErrorType.TIMEOUT,
        cause = this,
    )
    is IOException -> ErrorResponse(
        message = "Não foi possível conectar. Verifique sua internet.",
        type = ErrorType.NETWORK,
        cause = this,
    )
    else -> ErrorResponse(
        message = message ?: "Ocorreu um erro inesperado.",
        type = ErrorType.UNKNOWN,
        cause = this,
    )
}

