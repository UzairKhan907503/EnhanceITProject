package com.enhanceit.dashboard.ui.weatherdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.enhanceit.dashboard.data.AMMAN
import com.enhanceit.dashboard.data.MILLIS_UPDATED
import com.enhanceit.dashboard.data.getWeatherDomainModel
import com.enhanceit.dashboard.data.getWeatherForCityFlow
import com.enhanceit.dashboard.domain.usecases.WeatherDetailsUseCase
import com.enhanceit.dashboard.utils.MainCoroutinesRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherDetailsViewModelTest {
    private lateinit var viewModel: WeatherDetailsViewModel
    private var weatherDetailUseCase: WeatherDetailsUseCase = mockk(relaxed = true)
    private var savedStateHandle: SavedStateHandle = mockk()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        every { savedStateHandle.get<String>(any()) } returns AMMAN
    }

    @Test
    fun `WHEN given city name RETURNS weather details for that city`() = runTest {
        coEvery { weatherDetailUseCase.getWeatherDetails(any()) } returns getWeatherForCityFlow(
            MILLIS_UPDATED
        )
         viewModel = WeatherDetailsViewModel(
            weatherUseCase = weatherDetailUseCase,
            state = savedStateHandle
        )
        assertThat(viewModel.progress.value).isTrue()

        viewModel.state.take(2).toList().run {
            assertThat(get(0)).isEqualTo(WeatherDetailsStates.Ideal)
            assertThat(viewModel.progress.value).isFalse()
            assertThat(get(1)).isEqualTo(
                WeatherDetailsStates.WeatherInfoReceived(
                    getWeatherDomainModel(MILLIS_UPDATED, AMMAN)
                )
            )
        }
    }
}