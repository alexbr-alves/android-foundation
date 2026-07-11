package com.alexbralves.android.rxjava.tests

import com.alexbralves.android.rxjava.core.ErrorType
import com.alexbralves.android.rxjava.extensions.toErrorResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException

class ThrowableExtensionsTest {
    @Test
    fun `mapeia IOException como erro de rede`() {
        assertEquals(ErrorType.NETWORK, IOException().toErrorResponse().type)
    }

    @Test
    fun `mapeia timeout antes de IOException`() {
        assertEquals(ErrorType.TIMEOUT, SocketTimeoutException().toErrorResponse().type)
    }
}

