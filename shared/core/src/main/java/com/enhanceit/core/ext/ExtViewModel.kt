package com.enhanceit.core.ext

import androidx.navigation.NavOptions
import com.enhanceit.core.navigationflows.MainActivityNavigationFlow
import com.enhanceit.core.ui.activityviewmodel.MainActivityViewModel

suspend fun MainActivityViewModel.navigate(
    destination: MainActivityNavigationFlow,
    builder: NavOptions.Builder? = null
) {
    activityIntents.send(
        MainActivityViewModel.LaunchActivityEvents.NavigateToFlow(
            destination,
            builder
        )
    )
}