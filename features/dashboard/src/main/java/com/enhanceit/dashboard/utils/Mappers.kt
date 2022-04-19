package com.enhanceit.dashboard.utils

import com.enhanceit.dashboard.data.remote.models.responsemodels.WeatherResponseDTO
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import com.enhanceit.local.persistence.models.WeatherEntity

fun WeatherEntity.toDomainModel(): WeatherInfo {
    return WeatherInfo(
        cityName,
        timestamp,
        visibility,
        tempC,
        tempF,
        humidity,
        pressure,
        weatherDesc,
        weatherIconUrl
    )
}

fun WeatherInfo.fromDomain(): WeatherEntity {
    return WeatherEntity(
        cityName,
        timestamp,
        visibility,
        tempC,
        tempF,
        humidity,
        pressure,
        weatherDesc,
        weatherIconUrl
    )
}

fun WeatherResponseDTO.toDomainModel() = weatherInfo.currentCondition[0].run {
    WeatherInfo(
        cityName = weatherInfo.request[0].query,
        visibility = visibility,
        tempC = tempC,
        tempF = tempF,
        humidity = humidity,
        pressure = pressure,
        weatherDesc = weatherDesc[0].value,
        weatherIconUrl = weatherIconUrl[0].value,
        timestamp = System.currentTimeMillis().toString()
    )
}

fun List<WeatherEntity>.toDomainModel() =  map {
    it.toDomainModel()
}