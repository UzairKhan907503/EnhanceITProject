package com.enhanceit.local.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey
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


