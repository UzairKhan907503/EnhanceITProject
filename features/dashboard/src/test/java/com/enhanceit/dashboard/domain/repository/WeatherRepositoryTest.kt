package com.enhanceit.dashboard.domain.repository

import com.enhanceit.dashboard.data.*
import com.enhanceit.dashboard.data.remote.models.requestmodels.WeatherInputModel
import com.enhanceit.dashboard.domain.datasources.local.WeatherInfoPersistenceDataSource
import com.enhanceit.dashboard.domain.datasources.remote.WeatherInfoRemoteDataSource
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import com.enhanceit.remote.utils.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {
    private lateinit var weatherRepository: WeatherRepository
    private val persistenceDataSource: WeatherInfoPersistenceDataSource = mockk(relaxed = true)

    private val remoteDataSource: WeatherInfoRemoteDataSource = mockk(relaxed = true)

    @Before
    fun setup() {
        weatherRepository = WeatherRepositoryImpl(
            persistenceDataSource = persistenceDataSource,
            remoteDataSource = remoteDataSource
        )
    }

    /**
     * getWeatherDetails scenarios start here
     */
    @Test
    fun `WHEN given city name and data is not persisted getWeatherDetails RETURNS valid weather details from server`() =
        runTest {
            val data = slot<WeatherInfo>()
            var weatherInfo: WeatherInfo? = null
            every { remoteDataSource.getWeatherInfoForCity(any()) } returns getWeatherForCityFlow()

            coEvery { persistenceDataSource.addWeatherInfo(capture(data)) } coAnswers {
                weatherInfo = data.captured
            }
            coEvery { persistenceDataSource.deleteWeatherForCity(any()) } returns Unit
            coEvery { persistenceDataSource.getWeatherInfoForCity(any()) } returns flow {
                emit(
                    weatherInfo
                )
            }

            weatherRepository.getWeatherDetails(
                inputModel = WeatherInputModel(AMMAN)
            ).take(2).toList().run {
                assertThat(get(0)).isEqualTo(Resource.Loading(null))
                assertThat(get(1)).isEqualTo(Resource.Valid(getWeatherDomainModel(MILLIS_UPDATED)))
            }
        }

    @Test
    fun `WHEN given city name and data is not persisted without network connection getWeatherDetails RETURNS invalid`() =
        runTest {
            every { remoteDataSource.getWeatherInfoForCity(any()) } returns getInvalidResource()
            coEvery { persistenceDataSource.getWeatherInfoForCity(any()) } returns flow { emit(null) }
            weatherRepository.getWeatherDetails(
                inputModel = WeatherInputModel(AMMAN)
            ).first().let {
                assertThat(it).isEqualTo(Resource.Invalid<WeatherInfo>(NETWORK_ERROR))
            }
        }

    @Test
    fun `WHEN given city name and data is persisted without network connection getWeatherDetails RETURNS invalid then valid`() =
        runTest {
            every { remoteDataSource.getWeatherInfoForCity(any()) } returns getInvalidResource()
            coEvery { persistenceDataSource.getWeatherInfoForCity(any()) } returns flow {
                emit(
                    getWeatherDomainModel()
                )
            }

            weatherRepository.getWeatherDetails(
                inputModel = WeatherInputModel(AMMAN)
            ).take(2).toList().run {
                assertThat(get(0)).isEqualTo(Resource.Invalid<WeatherInfo>(NETWORK_ERROR))
                assertThat(get(1)).isEqualTo(Resource.Valid(getWeatherDomainModel()))
            }
        }

    @Test
    fun `WHEN given city name and outdated data is persisted getWeatherDetails RETURNS valid from remote`() =
        runTest {
            val data = slot<WeatherInfo>()
            var weatherInfo: WeatherInfo = getWeatherDomainModel()

            coEvery { persistenceDataSource.addWeatherInfo(capture(data)) } coAnswers {
                weatherInfo = data.captured
            }
            every { remoteDataSource.getWeatherInfoForCity(any()) } returns getWeatherForCityFlow()
            coEvery { persistenceDataSource.getWeatherInfoForCity(any()) } returns flow {
                emit(
                    weatherInfo
                )
            }

            weatherRepository.getWeatherDetails(
                inputModel = WeatherInputModel(AMMAN)
            ).take(2).toList().run {
                assertThat(get(0)).isEqualTo(Resource.Loading<WeatherInfo>())
                assertThat(get(1)).isEqualTo(Resource.Valid(getWeatherDomainModel(MILLIS_UPDATED)))
            }
        }

    /**
     * getAllWeathers scenarios starts here
     */
    @Test
    fun `WHEN data is persisted getAllWeathers RETURNS list of weather info`() = runTest {
        coEvery { persistenceDataSource.getWeatherForAll() } returns flow { emit(getWeathersList()) }
        weatherRepository.getAllWeathers().first().let {
            assertThat(it).isEqualTo(getWeathersList())
        }
    }

    @Test
    fun `WHEN data is not persisted getAllWeathers RETURNS empty list`() = runTest {
        coEvery { persistenceDataSource.getWeatherForAll() } returns flow { emit(listOf()) }
        weatherRepository.getAllWeathers().first().let {
            assertThat(it).isEqualTo(listOf<WeatherInfo>())
        }
    }


}