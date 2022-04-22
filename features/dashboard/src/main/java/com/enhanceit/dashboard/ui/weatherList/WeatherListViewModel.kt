package com.enhanceit.dashboard.ui.weatherList


import android.app.Application
import androidx.lifecycle.viewModelScope
import com.enhanceit.core.ui.base.BaseViewModel
import com.enhanceit.dashboard.R
import com.enhanceit.dashboard.data.remote.models.requestmodels.WeatherInputModel
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import com.enhanceit.dashboard.domain.usecases.WeatherDetailsUseCase
import com.enhanceit.remote.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val weatherUseCase: WeatherDetailsUseCase,
    app: Application
) : BaseViewModel() {
    private var intents = Channel<WeatherListEvents>()
    val event get() = intents.receiveAsFlow()
    private val weatherState = MutableStateFlow<WeatherListStates>(WeatherListStates.Ideal)
    val state = weatherState.asStateFlow()

    private val defaultList = listOf(
        app.getString(R.string.amman),
        app.getString(R.string.irbid),
        app.getString(R.string.aqaba)
    )

    val onItemClicked: (String) -> Unit = { city ->
        viewModelScope.launch {
            intents.send(WeatherListEvents.CitySelected(city))
        }
    }
    val onSearchClicked: (String) -> Unit = { city ->
        getCityWeather(city)
    }

    init {
        viewModelScope.launch {
            fetchDefaultDetails()
        }
    }

    private fun fetchDefaultDetails() = viewModelScope.launch {
        defaultList.forEach {
            getCityWeather(it)
        }
    }


    private fun getCityWeather(city: String) {
        showLoader()
        viewModelScope.launch {
            weatherUseCase.getWeatherDetails(WeatherInputModel(city)).collect {
                when (it) {
                    is Resource.Invalid -> {
                        hideLoader()
                        if (it.message.isNotBlank()) {
                            sendError(it.message)
                        }
                    }
                    is Resource.Valid -> {
                        hideLoader()
                        getAllData()
                    }
                    is Resource.Loading -> {
                        showLoader()
                    }
                }
            }
        }
    }

    private suspend fun getAllData() {
        val weatherList = weatherUseCase.getAllWeathers().value.sortedByDescending{
            it.timestamp
        }
        weatherState.value = WeatherListStates.WeatherDetailsFetched(weatherList)
    }


}