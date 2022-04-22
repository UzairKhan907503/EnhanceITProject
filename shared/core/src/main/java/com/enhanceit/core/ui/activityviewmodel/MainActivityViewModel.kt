package com.enhanceit.core.ui.activityviewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptions
import com.enhanceit.core.navigationflows.MainActivityNavigationFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow

class MainActivityViewModel : ViewModel() {

    var activityIntents = Channel<LaunchActivityEvents>()
    val event get() = activityIntents.receiveAsFlow()


    sealed class LaunchActivityEvents {
        data class NavigateToFlow(
            val destination: MainActivityNavigationFlow,
            val navOptions: NavOptions.Builder?
        ) : LaunchActivityEvents()
    }
}
