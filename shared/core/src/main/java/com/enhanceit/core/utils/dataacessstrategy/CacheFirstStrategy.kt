package com.enhanceit.core.utils.dataacessstrategy

import com.enhanceit.remote.utils.Resource
import kotlinx.coroutines.flow.*


fun <T> cacheFirstStrategy(
    shouldGetFromRemote: (T?) -> Boolean,
    getFromCache: (() -> Flow<T?>),
    getFromRemote: (() -> Flow<Resource<T>>),
    updateCache: (suspend (T) -> Unit)
): Flow<Resource<T>> = flow {

    getFromCache().collect { cacheData ->
        if (shouldGetFromRemote(cacheData).not()) {
            emit(Resource.Valid(cacheData!!))
        } else {
            getFromRemote().collect { remoteResponse ->
                when (remoteResponse) {
                    is Resource.Valid -> {
                        updateCache(remoteResponse.data)
                    }
                    is Resource.Invalid -> emit(Resource.Invalid(remoteResponse.message))
                    is Resource.Loading -> emit(Resource.Loading())

                }
            }

            getFromCache().collect { response ->
                response?.let {
                    emit(Resource.Valid(it))
                } ?: Resource.Invalid<T>("")
            }
        }
    }
}
