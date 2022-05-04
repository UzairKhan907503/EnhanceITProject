package com.enhanceit.dashboard.ui.weatherList

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enhanceit.core.ui.base.BaseViewModel
import com.enhanceit.dashboard.R
import com.enhanceit.dashboard.data.*
import com.enhanceit.dashboard.data.remote.models.requestmodels.WeatherInputModel
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import com.enhanceit.dashboard.domain.usecases.WeatherDetailsUseCase
import com.enhanceit.dashboard.utils.MainCoroutinesRule
import com.enhanceit.remote.utils.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherListViewModelTest {
    private val useCase: WeatherDetailsUseCase = mockk()
    private val app: Application = mockk()
    private lateinit var viewModel: WeatherListViewModel


    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        every { app.getString(R.string.irbid) } returns IRBID
        every { app.getString(R.string.aqaba) } returns AQABA
        every { app.getString(R.string.amman) } returns AMMAN
        viewModel = WeatherListViewModel(
            weatherUseCase = useCase,
            app = app
        )
    }

    @Test
    fun `WHEN given city list RETURNS weather info for given cities`() = runTest {
        val input = slot<WeatherInputModel>()
        val list = mutableListOf<WeatherInfo>()
        val flow = MutableStateFlow(mutableListOf<WeatherInfo>())
        coEvery { useCase.getAllWeathers() } returns flow

        coEvery { useCase.getWeatherDetails(capture(input)) } answers {
            list.add(getWeatherDomainModel(MILLIS_UPDATED, input.captured.city))
            flow.value = list
            flow {
                emit(Resource.Loading())
                emit(Resource.Valid(getWeatherDomainModel(MILLIS_UPDATED, input.captured.city)))
            }
        }

        viewModel.getAllData()
        viewModel.state.first().let {
            assertThat(it).isEqualTo(
                WeatherListStates.WeatherDetailsFetched(
                    list
                )
            )
        }
    }

    @Test
    fun `WHEN given city list and data is not available RETURNS error event`() = runTest {
        coEvery { useCase.getAllWeathers() } returns flowOf(listOf())
        coEvery { useCase.getWeatherDetails(any()) } returns getInvalidResource()
        viewModel.getAllData()
        viewModel.state.first().let {
            assertThat(it).isEqualTo(WeatherListStates.Ideal)
        }
        viewModel.baseEvents.first().let {
            assertThat(it).isEqualTo(BaseViewModel.BaseEvent.EventError(NETWORK_ERROR))
        }
    }

    @Test
    fun `WHEN given city name RETURNS weather info for given city`() = runTest {
        coEvery { useCase.getWeatherDetails(any()) } answers {
            flowOf(Resource.Valid(getWeatherDomainModel()))
        }
        viewModel.onSearchClicked(AMMAN)
        coVerify { useCase.getWeatherDetails(any()) }
    }

    @Test
    fun `WHEN call show loader RETURNS true for loader`() = runTest {
        viewModel.showLoader()
        assertThat(viewModel.progress.value).isEqualTo(true)
    }

    @Test
    fun `WHEN given city is selected RETURNS City selected event`() = runTest {
        viewModel.onItemClicked(AMMAN)

        viewModel.event.first().let {
            assertThat(it).isEqualTo(WeatherListEvents.CitySelected(AMMAN))
        }
    }

}