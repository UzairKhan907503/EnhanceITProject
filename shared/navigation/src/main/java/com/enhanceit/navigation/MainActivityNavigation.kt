package com.enhanceit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.enhanceit.core.navigationflows.MainActivityNavigationFlow

interface MainActivityNavigation {
    fun navigateToFlow(
        navController: NavController,
        flowMainActivity: MainActivityNavigationFlow,
        navOptions: NavOptions.Builder?
    )
}