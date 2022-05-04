package com.enhanceit.dashboard.data

import com.enhanceit.dashboard.data.remote.models.responsemodels.WeatherResponseDTO
import com.enhanceit.dashboard.data.remote.models.responsemodels.WeatherResponseDTO.Data.CurrentCondition.*
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import com.enhanceit.dashboard.utils.toDomainModel
import com.enhanceit.remote.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

const val AMMAN = "Amman, Jordan"
const val IRBID = " Irbid, Jordan"
const val AQABA = "Aqaba, Jordan"
const val MILLIS_UPDATED = "908709870948719087098"
const val MILLIS_OUTDATED = "1000"
const val NETWORK_ERROR = "Network is not available!"


fun getWeatherDetailsForCity(name: String) = WeatherResponseDTO(
    weatherInfo = WeatherResponseDTO.Data(
        currentCondition = listOf(
            WeatherResponseDTO.Data.CurrentCondition(
                humidity = "10",
                pressure = "500",
                tempC = "35",
                tempF = "86",
                visibility = "100",
                weatherDesc = listOf(
                    WeatherDesc(
                        value = "Warm Day Light"
                    )
                ),
                weatherIconUrl = listOf(
                    WeatherIconUrl(
                        value = "/details.png"
                    )
                )
            )
        ),
        request = listOf(
            WeatherResponseDTO.Data.Request(
                query = name,
                type = "city"
            )
        )
    )
)

fun getWeathersList(millis: String = MILLIS_OUTDATED) = listOf(
    getWeatherDetailsForCity("Amman, Jordan").toDomainModel(millis),
    getWeatherDetailsForCity("Islamabad, Pakistan").toDomainModel(millis)
)

fun getWeatherForCityFlow(millis: String = MILLIS_UPDATED): Flow<Resource<WeatherInfo>> = flow {
    emit(Resource.Loading(null))
    delay(200)
    emit(Resource.Valid(getWeatherDomainModel(millis)))
}

fun getWeatherDomainModel(millis: String = MILLIS_OUTDATED, city : String = AMMAN) =
    getWeatherDetailsForCity(city).toDomainModel(millis)

fun getInvalidResource(): Flow<Resource<WeatherInfo>> = flow {
    emit(
        Resource.Invalid(
            NETWORK_ERROR
        )
    )
}


