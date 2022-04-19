package com.enhanceit.dashboard.domain.datasources.remote

import com.enhanceit.dashboard.data.remote.models.requestmodels.WeatherInputModel
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import com.enhanceit.remote.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherInfoRemoteDataSource {
    fun getWeatherInfoForCity(inputModel: WeatherInputModel) : Flow<Resource<WeatherInfo>>
}