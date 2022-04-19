package com.enhanceit.core.utils.dataacessstrategy

import com.enhanceit.remote.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CachedDataAccessStrategy {
    suspend fun <T> performGetOperation(
        shouldGetFromRemote: (StateFlow<T?>) -> Boolean,
        getFromCache: (suspend () -> StateFlow<T?>),
        getFromRemote: (() -> Flow<Resource<T>>),
        updateCache: (suspend (T) -> Unit)
    ): StateFlow<Resource<T>>
}