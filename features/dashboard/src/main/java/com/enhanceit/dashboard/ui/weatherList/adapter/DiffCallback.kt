package com.enhanceit.dashboard.ui.weatherList.adapter

import androidx.recyclerview.widget.DiffUtil
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo


class DiffCallback : DiffUtil.ItemCallback<WeatherInfo>() {
    override fun areItemsTheSame(
        oldItem: WeatherInfo,
        newItem: WeatherInfo
    ) = oldItem.hashCode() == newItem.hashCode()

    override fun areContentsTheSame(
        oldItem: WeatherInfo,
        newItem: WeatherInfo
    ) = oldItem.hashCode() == newItem.hashCode()
}