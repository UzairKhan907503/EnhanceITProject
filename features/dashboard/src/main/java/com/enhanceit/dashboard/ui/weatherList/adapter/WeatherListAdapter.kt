package com.enhanceit.dashboard.ui.weatherList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enhanceit.dashboard.R
import com.enhanceit.dashboard.databinding.ItemSelectCityBinding
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo

class WeatherListAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<WeatherInfo, WeatherListAdapter.WeatherListViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListViewHolder =
        WeatherListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_select_city,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class WeatherListViewHolder(
        private val binding: ItemSelectCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    onClick.invoke(tvCity.text.toString())
                }
            }
        }

        fun bind(model : WeatherInfo) {
            binding.apply {
                city = model.cityName
            }
        }
    }
}