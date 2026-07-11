package com.alexbralves.android.coroutines.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexbralves.android.coroutines.extensions.toErrorResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    private val _error = MutableSharedFlow<ErrorResponse>(extraBufferCapacity = 1)

    val loading: StateFlow<Boolean> = _loading.asStateFlow()
    val error: SharedFlow<ErrorResponse> = _error.asSharedFlow()

    protected fun <T> execute(
        operation: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: ((ErrorResponse) -> Unit)? = null,
    ) = viewModelScope.launch {
        _loading.value = true
        try {
            onSuccess(withContext(ioDispatcher) { operation() })
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (throwable: Throwable) {
            val errorResponse = throwable.toErrorResponse()
            onError?.invoke(errorResponse) ?: _error.emit(errorResponse)
        } finally {
            _loading.value = false
        }
    }
}
