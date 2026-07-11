package com.alexbralves.android.rxjava.tests

import com.alexbralves.android.rxjava.core.AsyncResult
import com.alexbralves.android.rxjava.core.ErrorType
import com.alexbralves.android.rxjava.extensions.toAsyncResult
import io.reactivex.rxjava3.core.Observable
import org.junit.Test
import java.io.IOException

class ObservableExtensionsTest {
    @Test
    fun `emite loading e success`() {
        Observable.just("resultado")
            .toAsyncResult()
            .test()
            .assertValues(
                AsyncResult.Loading,
                AsyncResult.Success("resultado"),
            )
            .assertComplete()
    }

    @Test
    fun `converte throwable em failure`() {
        val observer = Observable.error<String>(IOException())
            .toAsyncResult()
            .test()

        observer.assertValueAt(0, AsyncResult.Loading)
        observer.assertValueAt(1) { result ->
            result is AsyncResult.Failure && result.error.type == ErrorType.NETWORK
        }
        observer.assertComplete()
    }
}

