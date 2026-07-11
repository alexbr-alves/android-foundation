package com.alexbralves.android.coroutines.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : Fragment() {
    private var _binding: VB? = null

    protected val binding: VB
        get() = checkNotNull(_binding) {
            "O binding só pode ser acessado entre onCreateView e onDestroyView."
        }

    protected abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = bindingFactory(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectFlow(viewModel.loading, ::showLoading)
        collectFlow(viewModel.error, ::showError)
        setupView()
        observeViewModel()
    }

    protected fun <T> collectFlow(flow: Flow<T>, collector: suspend (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect(collector)
            }
        }
    }

    protected open fun setupView() = Unit
    protected open fun observeViewModel() = Unit
    protected open fun showLoading(isLoading: Boolean) = Unit
    protected open fun showError(error: ErrorResponse) = Unit

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

