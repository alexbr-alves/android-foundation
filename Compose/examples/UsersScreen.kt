package com.alexbralves.android.compose.examples

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexbralves.android.compose.components.StateContent
import com.alexbralves.android.compose.extensions.CollectEffect
import com.alexbralves.android.compose.extensions.collectAsUiState

@Composable
fun UsersRoute(
    onNavigateToUser: (Long) -> Unit,
    // Forneça uma Factory quando a ViewModel tiver dependências no projeto real.
    viewModel: UsersViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsUiState()

    LaunchedEffect(Unit) { viewModel.onEvent(UsersEvent.Load) }
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is UsersEffect.NavigateToUser -> onNavigateToUser(effect.userId)
        }
    }

    UsersScreen(
        state = state,
        onRetry = { viewModel.onEvent(UsersEvent.Load) },
        onUserClick = { viewModel.onEvent(UsersEvent.SelectUser(it)) },
    )
}

@Composable
fun UsersScreen(
    state: UsersState,
    onRetry: () -> Unit,
    onUserClick: (User) -> Unit,
) {
    StateContent(state = state.users, onRetry = onRetry) { users ->
        Column {
            users.forEach { user ->
                Text(
                    text = user.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onUserClick(user) }
                        .padding(16.dp),
                )
            }
        }
    }
}
