package com.alexbralves.android.coroutines.tests

import com.alexbralves.android.coroutines.core.AsyncResult
import com.alexbralves.android.coroutines.core.ErrorType
import com.alexbralves.android.coroutines.extensions.asAsyncResult
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class FlowExtensionsTest {
    @Test
    fun `emite loading e success`() = runTest {
        val results = flow { emit("resultado") }.asAsyncResult().toList()

        assertEquals(
            listOf(AsyncResult.Loading, AsyncResult.Success("resultado")),
            results,
        )
    }

    @Test
    fun `converte throwable em failure`() = runTest {
        val results = flow<String> { throw IOException() }.asAsyncResult().toList()
        val failure = results.last() as AsyncResult.Failure

        assertEquals(ErrorType.NETWORK, failure.error.type)
    }
}

