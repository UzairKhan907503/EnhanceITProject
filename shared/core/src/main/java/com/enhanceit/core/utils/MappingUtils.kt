package com.enhanceit.core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlin.coroutines.coroutineContext

suspend fun <T, V> getDataFromDataBase(
    action: () -> Flow<T>,
    mapper: T.() -> V
): StateFlow<V> {
    return action.invoke()
        .distinctUntilChanged()
        .map {
            it.mapper()
        }.stateIn(CoroutineScope(coroutineContext))
}

