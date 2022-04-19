package com.enhanceit.dashboard.ui.weatherdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.enhanceit.core.ext.launchWhenStarted
import com.enhanceit.core.ui.base.BaseFragmentWithVM
import com.enhanceit.dashboard.R
import com.enhanceit.dashboard.databinding.FragmentWeatherDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WeatherDetailsFragment :
    BaseFragmentWithVM<FragmentWeatherDetailsBinding, WeatherDetailsViewModel>() {
    private val mViewModel by viewModels<WeatherDetailsViewModel>()
    override fun getLayoutId(): Int = R.layout.fragment_weather_details

    override fun getViewModel(): WeatherDetailsViewModel = mViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    @SuppressLint("SetTextI18n")
    private fun initObservers() {
        launchWhenStarted {
            getViewModel().state.collect { state ->
                when (state) {
                    is WeatherDetailsStates.WeatherInfoReceived -> {
                        binding.apply {
                            state.model.run {
                                tvCity.text = cityName
                                tvStatus.text = weatherDesc
                                tvTemp.text = "$tempC°C"
                                tvPressure.text = pressure
                                tvHumidity.text = humidity
                                tvVisibility.text = visibility
                            }
                        }
                    }
                    else -> {}
                }
            }
        }

    }


}