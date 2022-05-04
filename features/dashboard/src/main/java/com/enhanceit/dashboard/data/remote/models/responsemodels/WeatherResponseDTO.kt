package com.enhanceit.dashboard.data.remote.models.responsemodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDTO(
    @SerialName("data")
    val weatherInfo: Data
) {
    @Serializable
    data class Data(
        @SerialName("current_condition")
        val currentCondition: List<CurrentCondition>,
        @SerialName("request")
        val request: List<Request>,
    ) {

        @Serializable
        data class CurrentCondition(
            @SerialName("humidity")
            val humidity: String,
            @SerialName("pressure")
            val pressure: String,
            @SerialName("temp_C")
            val tempC: String,
            @SerialName("temp_F")
            val tempF: String,
            @SerialName("visibility")
            val visibility: String,
            @SerialName("weatherDesc")
            val weatherDesc: List<WeatherDesc>,
            @SerialName("weatherIconUrl")
            val weatherIconUrl: List<WeatherIconUrl>,
        ) {
            @Serializable
            data class WeatherDesc(
                @SerialName("value")
                val value: String
            )

            @Serializable
            data class WeatherIconUrl(
                @SerialName("value")
                val value: String
            )
        }

        @Serializable
        data class Request(
            @SerialName("query")
            val query: String,
            @SerialName("type")
            val type: String
        )
    }
}