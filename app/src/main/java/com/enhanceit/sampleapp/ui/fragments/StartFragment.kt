package com.enhanceit.sampleapp.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import com.enhanceit.core.ext.launchWhenStarted
import com.enhanceit.core.navigationflows.MainActivityNavigationFlow
import com.enhanceit.core.ui.activityviewmodel.MainActivityViewModel
import com.enhanceit.core.ui.base.BaseFragment
import com.enhanceit.sampleapp.R
import com.enhanceit.sampleapp.databinding.FragmentStartBinding

class StartFragment : BaseFragment<FragmentStartBinding>() {
    private val activityViewModel by activityViewModels<MainActivityViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateToDashboard()
        }, 2000)
    }

    private fun navigateToDashboard() {

        launchWhenStarted {
            activityViewModel.activityIntents.send(
                MainActivityViewModel.LaunchActivityEvents.NavigateToFlow(
                    MainActivityNavigationFlow.Dashboard,
                    NavOptions.Builder().setPopUpTo(R.id.start_fragment, true)
                )
            )
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_start
}