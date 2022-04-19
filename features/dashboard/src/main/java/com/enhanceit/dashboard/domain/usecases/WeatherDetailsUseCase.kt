package com.enhanceit.dashboard.domain.usecases

import com.enhanceit.core.utils.dataacessstrategy.CacheFirstStrategy
import com.enhanceit.dashboard.data.remote.models.requestmodels.WeatherInputModel
import com.enhanceit.dashboard.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherDetailsUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend fun getWeatherDetails(input: WeatherInputModel) =
        weatherRepository.getWeatherDetails(CacheFirstStrategy, input)

    suspend fun getAllWeathers() = weatherRepository.getAllWeathers()
}