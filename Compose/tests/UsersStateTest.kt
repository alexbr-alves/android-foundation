package com.alexbralves.android.compose.tests

import com.alexbralves.android.compose.core.UiState
import com.alexbralves.android.compose.examples.User
import com.alexbralves.android.compose.examples.UsersState
import org.junit.Assert.assertEquals
import org.junit.Test

class UsersStateTest {
    @Test
    fun `estado inicial permanece ocioso`() {
        assertEquals(UiState.Idle, UsersState().users)
    }

    @Test
    fun `success armazena usuarios`() {
        val users = listOf(User(1, "Ada"))
        val state = UsersState(users = UiState.Success(users))

        assertEquals(users, (state.users as UiState.Success<List<User>>).data)
    }
}
