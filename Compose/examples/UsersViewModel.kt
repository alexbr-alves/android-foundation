package com.alexbralves.android.compose.examples

import androidx.lifecycle.viewModelScope
import com.alexbralves.android.compose.core.BaseViewModel
import com.alexbralves.android.compose.core.UiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class UsersViewModel(
    private val repository: UserRepository,
) : BaseViewModel<UsersState, UsersEvent, UsersEffect>(UsersState()) {

    override fun handleEvent(event: UsersEvent) {
        when (event) {
            UsersEvent.Load -> loadUsers()
            is UsersEvent.SelectUser -> sendEffect(
                UsersEffect.NavigateToUser(event.user.id),
            )
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            setState { copy(users = UiState.Loading) }

            try {
                val users = repository.getUsers()
                setState { copy(users = UiState.Success(users)) }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (throwable: Throwable) {
                setState {
                    copy(
                        users = UiState.Error(
                            message = throwable.message ?: "Erro ao carregar usuários.",
                            cause = throwable,
                        ),
                    )
                }
            }
        }
    }
}

