package com.enhanceit.dashboard.domain.repository

import com.enhanceit.core.utils.dataacessstrategy.CachedDataAccessStrategy
import com.enhanceit.dashboard.data.remote.models.requestmodels.WeatherInputModel
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import com.enhanceit.remote.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherDetails(
        dataAccessStrategy: CachedDataAccessStrategy,
        inputModel: WeatherInputModel
    ): Flow<Resource<WeatherInfo>>

    suspend fun getAllWeathers() : Flow<List<WeatherInfo>>
}