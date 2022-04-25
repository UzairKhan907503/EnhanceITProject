package com.enhanceit.dashboard.data.local.datasources

import com.enhanceit.dashboard.domain.datasources.local.WeatherInfoPersistenceDataSource
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import com.enhanceit.dashboard.utils.fromDomain
import com.enhanceit.dashboard.utils.toDomainModel
import com.enhanceit.local.persistence.daos.WeatherInfoDao
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class WeatherInfoPersistenceDataSourceImpl @Inject constructor(
    private val dao: WeatherInfoDao
) : WeatherInfoPersistenceDataSource {
    override fun getWeatherInfoForCity(city: String): Flow<WeatherInfo?> {
        return dao.getWeatherForCity(city).map {
                it?.toDomainModel()
            }
    }

    override suspend fun addWeatherInfo(weatherInfo: WeatherInfo) {
        dao.insert(weatherInfo.fromDomain())
    }

    override suspend fun getWeatherForAll(): Flow<List<WeatherInfo>> {
        return dao.getAllWeatherInfo().map {
                it.toDomainModel()
            }
    }

    override suspend fun deleteWeatherForCity(city: String) {
        dao.delete(city)
    }
}