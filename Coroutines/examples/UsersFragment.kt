package com.alexbralves.android.coroutines.examples

import androidx.fragment.app.viewModels
import com.alexbralves.android.coroutines.core.BaseFragment
import com.alexbralves.android.coroutines.core.ErrorResponse
import com.example.databinding.FragmentUsersBinding

/** Ajuste o import do binding e forneça uma ViewModel.Factory no projeto real. */
class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {
    override val viewModel: UsersViewModel by viewModels()

    override fun setupView() {
        binding.retryButton.setOnClickListener { viewModel.loadUsers() }
        viewModel.loadUsers()
    }

    override fun observeViewModel() {
        collectFlow(viewModel.users) { users ->
            binding.contentText.text = users.joinToString { it.name }
        }
    }

    override fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE
        else android.view.View.GONE
    }

    override fun showError(error: ErrorResponse) {
        binding.contentText.text = error.message
    }
}

