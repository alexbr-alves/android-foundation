package com.alexbralves.android.rxjava.examples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexbralves.android.rxjava.core.BaseViewModel

class UsersViewModel(
    private val repository: UserRepository,
) : BaseViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun loadUsers() {
        subscribe(
            observable = repository.getUsers(),
            onSuccess = { _users.value = it },
        )
    }
}

