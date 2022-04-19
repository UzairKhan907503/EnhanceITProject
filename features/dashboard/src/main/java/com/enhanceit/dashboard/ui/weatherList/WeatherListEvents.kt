package com.enhanceit.dashboard.ui.weatherList

sealed class WeatherListEvents {
    data class CitySelected(val city: String) : WeatherListEvents()
}