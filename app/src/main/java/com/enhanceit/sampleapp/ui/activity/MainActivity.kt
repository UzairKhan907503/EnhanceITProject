package com.enhanceit.sampleapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.enhanceit.core.ui.activityviewmodel.MainActivityViewModel
import com.enhanceit.navigation.MainActivityNavigation
import com.enhanceit.sampleapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mViewModel by viewModels<MainActivityViewModel>()

    @Inject
    lateinit var launchActivityNavigation: MainActivityNavigation
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            mViewModel.event.collect { events ->
                when (events) {
                    is MainActivityViewModel.LaunchActivityEvents.NavigateToFlow -> {
                        launchActivityNavigation.navigateToFlow(
                            navController, events.destination, events.navOptions
                        )
                    }
                }
            }
        }
    }
}