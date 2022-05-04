package com.enhanceit.dashboard.domain.usecases

import com.enhanceit.dashboard.data.*
import com.enhanceit.dashboard.data.remote.models.requestmodels.WeatherInputModel
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import com.enhanceit.dashboard.domain.repository.WeatherRepository
import com.enhanceit.remote.utils.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherDetailsUseCaseTest {

    private lateinit var weatherDetailsUseCase: WeatherDetailsUseCase
    private val weatherRepository: WeatherRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        weatherDetailsUseCase = WeatherDetailsUseCase(weatherRepository)
    }


    @Test
    fun `WHEN given input model getWeatherDetails RETURNS resource of weather info`() =
        runTest {
            coEvery { weatherRepository.getWeatherDetails(any()) } returns getWeatherForCityFlow()
            weatherDetailsUseCase.getWeatherDetails(input = WeatherInputModel(AMMAN)).take(2)
                .toList().run {
                    assertThat(get(0)).isEqualTo(Resource.Loading<WeatherInfo>())
                    assertThat(get(1)).isEqualTo(Resource.Valid(getWeatherDomainModel(millis = MILLIS_UPDATED)))
                }
        }

    @Test
    fun `WHEN data is persisted RETURNS valid response from persistence`() =
        runTest {
            coEvery { weatherRepository.getAllWeathers() } returns flow { emit(getWeathersList()) }
            weatherDetailsUseCase.getAllWeathers().first().let {
                assertThat(it).isEqualTo(getWeathersList())
            }
        }
}