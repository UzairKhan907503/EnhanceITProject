package com.enhanceit.core.utils.dataacessstrategy

import com.enhanceit.remote.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CachedDataAccessStrategy {
    suspend fun <T> performGetOperation(
        shouldGetFromRemote: (T) -> Boolean,
        getFromCache: ( suspend () -> Flow<T?>),
        getFromRemote: (() -> Flow<Resource<T>>),
        updateCache: (suspend (T) -> Unit)
    ): Flow<Resource<T>>
}