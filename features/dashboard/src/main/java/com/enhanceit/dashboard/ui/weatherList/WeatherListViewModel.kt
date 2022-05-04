package com.enhanceit.dashboard.ui.weatherList


import android.app.Application
import androidx.lifecycle.viewModelScope
import com.enhanceit.core.ui.base.BaseViewModel
import com.enhanceit.dashboard.R
import com.enhanceit.dashboard.data.remote.models.requestmodels.WeatherInputModel
import com.enhanceit.dashboard.domain.usecases.WeatherDetailsUseCase
import com.enhanceit.remote.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
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
    val state get() = weatherState.asStateFlow()

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
        viewModelScope.launch {
            getCityWeather(city)
        }
    }

    private suspend fun fetchDefaultDetails(list: List<String> = defaultList) {
        coroutineScope {
            list.forEach {
                launch { getCityWeather(it) }
            }
        }
    }


    private suspend fun getCityWeather(city: String) {
        showLoader()
        weatherUseCase.getWeatherDetails(WeatherInputModel(city)).collect {
            when (it) {
                is Resource.Invalid -> {
                    hideLoader()
                    if (it.message.isNotBlank()) {
                        sendError(it.message)
                    }
                }
                is Resource.Loading -> {
                    showLoader()
                }
                else -> {
                    hideLoader()
                }
            }
        }
    }

    fun getAllData() = viewModelScope.launch {
        weatherUseCase.getAllWeathers().collect { response ->
            if (response.isEmpty()) {
                fetchDefaultDetails()
                return@collect
            }

            response.run {
                val list = sortedByDescending { it.timestamp }
                weatherState.value = WeatherListStates.WeatherDetailsFetched(list)
                intents.send(WeatherListEvents.ItemAdded)
            }
            hideLoader()
        }
    }



}