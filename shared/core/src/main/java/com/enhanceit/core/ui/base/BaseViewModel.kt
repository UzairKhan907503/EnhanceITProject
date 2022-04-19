package com.enhanceit.core.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Created by Uzair Nawaz on 24-Sep-21.
 */
abstract class BaseViewModel : ViewModel() {

    private val _progress = MutableStateFlow(false)
    val progress get() = _progress.asStateFlow()

    val baseEventsChannel = Channel<BaseEvent>()
    val baseEvents = baseEventsChannel.receiveAsFlow()

    fun showLoader() {
        _progress.value = true
    }

    fun hideLoader() {
        _progress.value = false
    }

    suspend fun sendError(error: String?) {
        baseEventsChannel.send(BaseEvent.EventError(error ?: ""))
    }

    suspend fun sendSuccess(msg: String) {
        baseEventsChannel.send(BaseEvent.EventSuccess(msg))
    }

    sealed class BaseEvent {
        data class EventError(val msg: String = "") : BaseEvent()
        data class EventSuccess(val msg: String = "") : BaseEvent()
    }
}