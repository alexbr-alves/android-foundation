package com.alexbralves.android.coroutines.examples

import com.alexbralves.android.coroutines.core.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersViewModel(
    private val repository: UserRepository,
) : BaseViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    fun loadUsers() {
        execute(
            operation = repository::getUsers,
            onSuccess = { _users.value = it },
        )
    }
}

