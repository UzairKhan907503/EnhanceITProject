package com.enhanceit.dashboard.domain.datasources.local

import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherInfoPersistenceDataSource {
    fun getWeatherInfoForCity(city : String) : Flow<WeatherInfo?>
    suspend fun addWeatherInfo(weatherInfo: WeatherInfo)
    suspend fun getWeatherForAll() : Flow<List<WeatherInfo>>
    suspend fun deleteWeatherForCity(city : String)
}