package com.enhanceit.dashboard.ui.weatherList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.enhanceit.core.ext.*
import com.enhanceit.core.ui.base.BaseFragmentWithVM
import com.enhanceit.dashboard.BR
import com.enhanceit.dashboard.R
import com.enhanceit.dashboard.databinding.FragmentWeatherListBinding
import com.enhanceit.dashboard.ui.weatherList.adapter.WeatherListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WeatherListFragment :
    BaseFragmentWithVM<FragmentWeatherListBinding, WeatherListViewModel>() {
    private val mViewModel by viewModels<WeatherListViewModel>()
    private var adapter: WeatherListAdapter by autoCleaned()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            mViewModel.getAllData()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        adapter = WeatherListAdapter(getViewModel().onItemClicked)
        binding.rvCities.adapter = adapter
        binding.searchView.apply {
            doOnSubmit{
                hideKeyboard()
                getViewModel().onSearchClicked(it)
            }
        }

    }

    private fun initObservers() {
        launchWhenStarted {
            getViewModel().event.collect {
                when (it) {
                    is WeatherListEvents.CitySelected -> {
                        findNavController().navigate(
                            WeatherListFragmentDirections.actionWeatherListFragmentToWeatherDetailsFragment(
                                it.city
                            )
                        )
                    }
                    WeatherListEvents.ItemAdded -> {
                        binding.rvCities.smoothScrollToPosition(0)
                    }
                }
            }
        }

        launchWhenStarted {
            getViewModel().state.collect {
                when (it) {
                    is WeatherListStates.WeatherDetailsFetched -> {
                        adapter.submitList(it.cities)
                    }
                    else -> {}
                }
            }
        }
    }


    override fun getLayoutId(): Int = R.layout.fragment_weather_list
    override fun getViewModel(): WeatherListViewModel = mViewModel
    override fun getBindingVariable(): Int = BR.model


}