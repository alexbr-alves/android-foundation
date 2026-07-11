package com.alexbralves.android.compose.examples

import com.alexbralves.android.compose.core.UiEffect
import com.alexbralves.android.compose.core.UiEvent
import com.alexbralves.android.compose.core.UiState

data class User(val id: Long, val name: String)

data class UsersState(
    val users: UiState<List<User>> = UiState.Idle,
)

sealed interface UsersEvent : UiEvent {
    data object Load : UsersEvent
    data class SelectUser(val user: User) : UsersEvent
}

sealed interface UsersEffect : UiEffect {
    data class NavigateToUser(val userId: Long) : UsersEffect
}

