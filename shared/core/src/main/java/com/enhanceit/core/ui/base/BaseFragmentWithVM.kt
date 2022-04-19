package com.enhanceit.core.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.enhanceit.core.ext.launchWhenStarted
import com.enhanceit.core.ext.showError
import kotlinx.coroutines.flow.collect

abstract class BaseFragmentWithVM<T : ViewDataBinding, V : BaseViewModel> : BaseFragment<T>() {

    open fun getBindingVariable(): Int? = null
    abstract fun getViewModel(): V
    private fun performBinding() {
        getBindingVariable()?.let {
            binding.lifecycleOwner = this
            binding.setVariable(it, getViewModel())
            binding.executePendingBindings()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        performBinding()
        launchWhenStarted {
            getViewModel().progress.collect {
                showLoader(it)
            }
        }
        launchWhenStarted {
            getViewModel().baseEvents.collect { event ->
                when(event){
                    is BaseViewModel.BaseEvent.EventError -> {
                        showError(event.msg)
                    }
                    else -> {}}
                }
            }
    }
}