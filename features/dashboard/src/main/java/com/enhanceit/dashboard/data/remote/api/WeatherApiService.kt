package com.enhanceit.dashboard.data.remote.api

import com.enhanceit.dashboard.data.remote.models.responsemodels.WeatherResponseDTO
import com.enhanceit.remote.utils.ApiUtils
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET(ApiUtils.WEATHER_UPDATES)
    suspend fun getWeatherForCast(
        @Query("key") key: String = ApiUtils.API_KEY,
        @Query("q") city: String,
        @Query("format") format: String = "json",
    ): Response<WeatherResponseDTO>
}