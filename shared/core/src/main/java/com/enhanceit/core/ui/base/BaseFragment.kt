package com.enhanceit.core.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.enhanceit.core.ext.autoCleaned
import com.enhanceit.core.ext.progressBar

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    var binding: T by autoCleaned()
    private var loader : AlertDialog by autoCleaned()
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        loader = progressBar()
        return binding.root
    }

    private fun showLoading() {
        loader.show()
    }

    private fun hideLoading() {
        loader.hide()
    }

    fun showLoader(show: Boolean) {
        if (show) showLoading() else hideLoading()
    }
}