package com.alexbralves.android.compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<T>.collectAsUiState(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
): T {
    val state by collectAsStateWithLifecycle(minActiveState = minActiveState)
    return state
}

@Composable
fun <T> CollectEffect(
    flow: Flow<T>,
    onEffect: suspend (T) -> Unit,
) {
    LaunchedEffect(flow) {
        flow.collect(onEffect)
    }
}

