package com.alexbralves.android.rxjava.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

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
        observeBaseState()
        setupView()
        observeViewModel()
    }

    protected open fun setupView() = Unit

    protected open fun observeViewModel() = Unit

    protected open fun showLoading(isLoading: Boolean) = Unit

    protected open fun showError(error: ErrorResponse) = Unit

    private fun observeBaseState() {
        viewModel.loading.observe(viewLifecycleOwner, ::showLoading)
        viewModel.error.observe(viewLifecycleOwner, ::showError)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

