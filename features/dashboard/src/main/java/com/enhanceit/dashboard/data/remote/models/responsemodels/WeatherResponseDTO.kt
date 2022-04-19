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
        @SerialName("ClimateAverages")
        val climateAverages: List<ClimateAverage>,
        @SerialName("current_condition")
        val currentCondition: List<CurrentCondition>,
        @SerialName("request")
        val request: List<Request>,
        @SerialName("weather")
        val weather: List<Weather>
    ) {
        @Serializable
        data class ClimateAverage(
            @SerialName("month")
            val month: List<Month>
        ) {
            @Serializable
            data class Month(
                @SerialName("absMaxTemp")
                val absMaxTemp: String,
                @SerialName("absMaxTemp_F")
                val absMaxTempF: String,
                @SerialName("avgDailyRainfall")
                val avgDailyRainfall: String,
                @SerialName("avgMinTemp")
                val avgMinTemp: String,
                @SerialName("avgMinTemp_F")
                val avgMinTempF: String,
                @SerialName("index")
                val index: String,
                @SerialName("name")
                val name: String
            )
        }

        @Serializable
        data class CurrentCondition(
            @SerialName("cloudcover")
            val cloudcover: String,
            @SerialName("FeelsLikeC")
            val feelsLikeC: String,
            @SerialName("FeelsLikeF")
            val feelsLikeF: String,
            @SerialName("humidity")
            val humidity: String,
            @SerialName("observation_time")
            val observationTime: String,
            @SerialName("precipInches")
            val precipInches: String,
            @SerialName("precipMM")
            val precipMM: String,
            @SerialName("pressure")
            val pressure: String,
            @SerialName("pressureInches")
            val pressureInches: String,
            @SerialName("temp_C")
            val tempC: String,
            @SerialName("temp_F")
            val tempF: String,
            @SerialName("uvIndex")
            val uvIndex: String,
            @SerialName("visibility")
            val visibility: String,
            @SerialName("visibilityMiles")
            val visibilityMiles: String,
            @SerialName("weatherCode")
            val weatherCode: String,
            @SerialName("weatherDesc")
            val weatherDesc: List<WeatherDesc>,
            @SerialName("weatherIconUrl")
            val weatherIconUrl: List<WeatherIconUrl>,
            @SerialName("winddir16Point")
            val winddir16Point: String,
            @SerialName("winddirDegree")
            val winddirDegree: String,
            @SerialName("windspeedKmph")
            val windspeedKmph: String,
            @SerialName("windspeedMiles")
            val windspeedMiles: String
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

        @Serializable
        data class Weather(
            @SerialName("astronomy")
            val astronomy: List<Astronomy>,
            @SerialName("avgtempC")
            val avgtempC: String,
            @SerialName("avgtempF")
            val avgtempF: String,
            @SerialName("date")
            val date: String,
            @SerialName("hourly")
            val hourly: List<Hourly>,
            @SerialName("maxtempC")
            val maxtempC: String,
            @SerialName("maxtempF")
            val maxtempF: String,
            @SerialName("mintempC")
            val mintempC: String,
            @SerialName("mintempF")
            val mintempF: String,
            @SerialName("sunHour")
            val sunHour: String,
            @SerialName("totalSnow_cm")
            val totalSnowCm: String,
            @SerialName("uvIndex")
            val uvIndex: String
        ) {
            @Serializable
            data class Astronomy(
                @SerialName("moon_illumination")
                val moonIllumination: String,
                @SerialName("moon_phase")
                val moonPhase: String,
                @SerialName("moonrise")
                val moonrise: String,
                @SerialName("moonset")
                val moonset: String,
                @SerialName("sunrise")
                val sunrise: String,
                @SerialName("sunset")
                val sunset: String
            )

            @Serializable
            data class Hourly(
                @SerialName("chanceoffog")
                val chanceoffog: String,
                @SerialName("chanceoffrost")
                val chanceoffrost: String,
                @SerialName("chanceofhightemp")
                val chanceofhightemp: String,
                @SerialName("chanceofovercast")
                val chanceofovercast: String,
                @SerialName("chanceofrain")
                val chanceofrain: String,
                @SerialName("chanceofremdry")
                val chanceofremdry: String,
                @SerialName("chanceofsnow")
                val chanceofsnow: String,
                @SerialName("chanceofsunshine")
                val chanceofsunshine: String,
                @SerialName("chanceofthunder")
                val chanceofthunder: String,
                @SerialName("chanceofwindy")
                val chanceofwindy: String,
                @SerialName("cloudcover")
                val cloudcover: String,
                @SerialName("DewPointC")
                val dewPointC: String,
                @SerialName("DewPointF")
                val dewPointF: String,
                @SerialName("FeelsLikeC")
                val feelsLikeC: String,
                @SerialName("FeelsLikeF")
                val feelsLikeF: String,
                @SerialName("HeatIndexC")
                val heatIndexC: String,
                @SerialName("HeatIndexF")
                val heatIndexF: String,
                @SerialName("humidity")
                val humidity: String,
                @SerialName("precipInches")
                val precipInches: String,
                @SerialName("precipMM")
                val precipMM: String,
                @SerialName("pressure")
                val pressure: String,
                @SerialName("pressureInches")
                val pressureInches: String,
                @SerialName("tempC")
                val tempC: String,
                @SerialName("tempF")
                val tempF: String,
                @SerialName("time")
                val time: String,
                @SerialName("uvIndex")
                val uvIndex: String,
                @SerialName("visibility")
                val visibility: String,
                @SerialName("visibilityMiles")
                val visibilityMiles: String,
                @SerialName("weatherCode")
                val weatherCode: String,
                @SerialName("weatherDesc")
                val weatherDesc: List<WeatherDesc>,
                @SerialName("weatherIconUrl")
                val weatherIconUrl: List<WeatherIconUrl>,
                @SerialName("WindChillC")
                val windChillC: String,
                @SerialName("WindChillF")
                val windChillF: String,
                @SerialName("WindGustKmph")
                val windGustKmph: String,
                @SerialName("WindGustMiles")
                val windGustMiles: String,
                @SerialName("winddir16Point")
                val winddir16Point: String,
                @SerialName("winddirDegree")
                val winddirDegree: String,
                @SerialName("windspeedKmph")
                val windspeedKmph: String,
                @SerialName("windspeedMiles")
                val windspeedMiles: String
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
        }
    }
}