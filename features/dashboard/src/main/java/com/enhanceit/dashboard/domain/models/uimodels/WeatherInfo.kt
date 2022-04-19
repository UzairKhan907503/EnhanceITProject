package com.enhanceit.dashboard.domain.models.uimodels


data class WeatherInfo(
    val cityName: String,
    val timestamp: String,
    val visibility: String,
    val tempC: String,
    val tempF: String,
    val humidity : String,
    val pressure: String,
    val weatherDesc: String,
    val weatherIconUrl: String
)
