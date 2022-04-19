package com.enhanceit.core.ui.base

import com.enhanceit.remote.utils.Resource
import com.uzair.data.utils.NetworkUtils.getErrorMessage
import com.uzair.data.utils.NetworkUtils.getNetworkErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class BaseRemoteDataSource {

    /**
     * This method  will safely invoke the remote api call and return a flow of [Resource]s
     */
    fun <T : Any> safeApiCall( // TODO: Move this to a retrofit adapter
        call: suspend () -> Response<T>
    ): Flow<Resource<T>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(safeApiResult(call.invoke()))
            } catch (e: Exception) {
                emit(Resource.Invalid<T>(getNetworkErrorMessage(e)))
            }
        }.catch {
            emit(Resource.Invalid<T>(it.message ?: ""))
        }


    private fun <T> safeApiResult(response: Response<T>): Resource<T> =
        if (response.isSuccessful) Resource.Valid(response.body()!!)
        else Resource.Invalid(getErrorMessage(response.code()))

}