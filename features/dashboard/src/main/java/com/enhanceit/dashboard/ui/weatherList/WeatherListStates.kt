package com.enhanceit.dashboard.ui.weatherList

import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo

sealed class WeatherListStates {
    object Ideal : WeatherListStates()
    data class WeatherDetailsFetched(val cities: List<WeatherInfo>) :
        WeatherListStates()
}