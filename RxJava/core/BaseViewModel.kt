package com.alexbralves.android.rxjava.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexbralves.android.rxjava.extensions.applySchedulers
import com.alexbralves.android.rxjava.extensions.toErrorResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _loading = MutableLiveData(false)

    val loading: LiveData<Boolean> = _loading
    val error = SingleLiveEvent<ErrorResponse>()

    /**
     * Observa um fluxo que já representa Loading, Success e Failure.
     * Erros emitidos pelo canal onError também são tratados.
     */
    protected fun <T> subscribe(
        observable: Observable<AsyncResult<T>>,
        onSuccess: (T) -> Unit,
        onError: ((ErrorResponse) -> Unit)? = null,
    ) {
        observable
            .applySchedulers()
            .subscribe(
                { result ->
                    when (result) {
                        AsyncResult.Loading -> _loading.value = true
                        is AsyncResult.Success -> {
                            _loading.value = false
                            onSuccess(result.value)
                        }
                        is AsyncResult.Failure -> {
                            _loading.value = false
                            dispatchError(result.error, onError)
                        }
                    }
                },
                { throwable ->
                    _loading.value = false
                    dispatchError(throwable.toErrorResponse(), onError)
                },
            )
            .let(disposables::add)
    }

    /** Entrega o AsyncResult integralmente para estados específicos da tela. */
    protected fun <T> subscribeResult(
        observable: Observable<AsyncResult<T>>,
        onResult: (AsyncResult<T>) -> Unit,
    ) {
        observable
            .applySchedulers()
            .subscribe(onResult) { throwable ->
                onResult(AsyncResult.Failure(throwable.toErrorResponse()))
            }
            .let(disposables::add)
    }

    private fun dispatchError(
        value: ErrorResponse,
        handler: ((ErrorResponse) -> Unit)?,
    ) {
        handler?.invoke(value) ?: run { error.value = value }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}

