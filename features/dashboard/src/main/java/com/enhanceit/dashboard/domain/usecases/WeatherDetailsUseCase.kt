package com.enhanceit.dashboard.domain.usecases

import com.enhanceit.dashboard.data.remote.models.requestmodels.WeatherInputModel
import com.enhanceit.dashboard.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherDetailsUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend fun getWeatherDetails(input: WeatherInputModel) =
        weatherRepository.getWeatherDetails(input)

    suspend fun getAllWeathers() = weatherRepository.getAllWeathers()
}