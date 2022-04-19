package com.enhanceit.dashboard.ui.weatherdetails

import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo

sealed class WeatherDetailsStates{
    object Ideal : WeatherDetailsStates()
    data class WeatherInfoReceived(val model : WeatherInfo) : WeatherDetailsStates()
}
