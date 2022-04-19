package com.enhanceit.dashboard.domain.datasources.local

import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import kotlinx.coroutines.flow.StateFlow

interface WeatherInfoPersistenceDataSource {
    suspend fun getWeatherInfoForCity(city : String) : StateFlow<WeatherInfo?>
    suspend fun addWeatherInfo(weatherInfo: WeatherInfo)
    suspend fun getWeatherForAll() : StateFlow<List<WeatherInfo>>
    suspend fun deleteWeatherForCity(city : String)
}