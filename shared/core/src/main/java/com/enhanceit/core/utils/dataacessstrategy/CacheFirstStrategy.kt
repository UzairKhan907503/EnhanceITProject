package com.enhanceit.core.utils.dataacessstrategy

import com.enhanceit.remote.utils.Resource
import kotlinx.coroutines.flow.*

/**
 * This implementation of [CachedDataAccessStrategy] emits an empty [Resource.Loading] first
 * if the data is not updated in last hour then it fetches data from remote,
 * if successful that data is stored in cache, if unsuccessful,
 * it emits a [Resource.Invalid] object
 * At the end, the state flow emits all from the cache
 */
object CacheFirstStrategy : CachedDataAccessStrategy {

    override suspend fun <T> performGetOperation(
        shouldGetFromRemote: (T) -> Boolean,
        getFromCache: (suspend () -> Flow<T?>),
        getFromRemote: (() -> Flow<Resource<T>>),
        updateCache: (suspend (T) -> Unit)
    ): Flow<Resource<T>> = flow {

        getFromCache().collect { cache ->
            cache?.let {
                if (shouldGetFromRemote(it).not()) {
                    emit(Resource.Valid(it))
                }
            } ?: kotlin.run {
                getFromRemote().collect { remoteResponse ->
                    when (remoteResponse) {
                        is Resource.Valid -> updateCache(remoteResponse.data)
                        is Resource.Invalid -> emit(Resource.Invalid<T>(remoteResponse.message))
                        else -> emit(Resource.Loading())

                    }
                }
            }

        }

        getFromCache().collect { response ->
            response?.let {
                emit(Resource.Valid(it))
            } ?: Resource.Invalid<T>("")
        }
    }
}