package com.alexbralves.android.coroutines.extensions

import com.alexbralves.android.coroutines.core.AsyncResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.asAsyncResult(): Flow<AsyncResult<T>> =
    map<T, AsyncResult<T>> { AsyncResult.Success(it) }
        .onStart { emit(AsyncResult.Loading) }
        .catch { emit(AsyncResult.Failure(it.toErrorResponse())) }

