package com.enhanceit.dashboard.data.remote.datasources

import com.enhanceit.core.ui.base.BaseRemoteDataSource
import com.enhanceit.dashboard.data.remote.api.WeatherApiService
import com.enhanceit.dashboard.data.remote.models.requestmodels.WeatherInputModel
import com.enhanceit.dashboard.domain.datasources.remote.WeatherInfoRemoteDataSource
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import com.enhanceit.dashboard.utils.toDomainModel
import com.enhanceit.remote.utils.Resource
import com.enhanceit.remote.utils.transform
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherInfoRemoteDataSourceImpl @Inject constructor(
    private val apiService: WeatherApiService
) : BaseRemoteDataSource(), WeatherInfoRemoteDataSource {
    override fun getWeatherInfoForCity(inputModel: WeatherInputModel): Flow<Resource<WeatherInfo>> {
        return safeApiCall {
            apiService.getWeatherForCast(city = inputModel.city)
        }.map { response ->
            response.transform {
                it.toDomainModel()
            }
        }
    }
}