package com.alexbralves.android.compose.examples

import kotlinx.coroutines.delay

class UserRepository {
    suspend fun getUsers(): List<User> {
        delay(1_000)
        return listOf(User(1, "Ada Lovelace"), User(2, "Grace Hopper"))
    }
}

