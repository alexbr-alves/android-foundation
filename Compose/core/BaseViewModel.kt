package com.alexbralves.android.compose.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : Any, Event : UiEvent, Effect : UiEffect>(
    initialState: State,
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    private val effects = Channel<Effect>(Channel.BUFFERED)

    val state: StateFlow<State> = _state.asStateFlow()
    val effect: Flow<Effect> = effects.receiveAsFlow()

    protected val currentState: State
        get() = _state.value

    fun onEvent(event: Event) {
        handleEvent(event)
    }

    protected abstract fun handleEvent(event: Event)

    protected fun setState(reducer: State.() -> State) {
        _state.update(reducer)
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch { effects.send(effect) }
    }
}

