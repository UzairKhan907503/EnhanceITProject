package com.enhanceit.dashboard.ui.weatherdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.enhanceit.core.ui.base.BaseViewModel
import com.enhanceit.dashboard.data.remote.models.requestmodels.WeatherInputModel
import com.enhanceit.dashboard.domain.usecases.WeatherDetailsUseCase
import com.enhanceit.remote.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val weatherUseCase: WeatherDetailsUseCase,
    state: SavedStateHandle
) : BaseViewModel() {
    private val cityName = state.get<String>("city")!!
    private val _state = MutableStateFlow<WeatherDetailsStates>(WeatherDetailsStates.Ideal)
    val state = _state.asStateFlow()


    init {
        getWeatherDetails(cityName)
    }

    private fun getWeatherDetails(cityName: String) = viewModelScope.launch {
        weatherUseCase.getWeatherDetails(WeatherInputModel(cityName)).collect {
            when (it) {
                is Resource.Valid -> {
                    hideLoader()
                    _state.value = WeatherDetailsStates.WeatherInfoReceived(it.data)
                }
                is Resource.Invalid -> {
                    hideLoader()
                    if (it.message.isNotBlank()) {
                        sendError(it.message)
                    }
                }
                is Resource.Loading -> {
                    showLoader()
                }
            }
        }
    }
}