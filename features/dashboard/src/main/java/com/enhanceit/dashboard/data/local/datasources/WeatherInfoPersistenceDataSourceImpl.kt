package com.enhanceit.dashboard.data.local.datasources

import com.enhanceit.core.utils.getDataFromDataBase
import com.enhanceit.dashboard.domain.datasources.local.WeatherInfoPersistenceDataSource
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import com.enhanceit.dashboard.utils.fromDomain
import com.enhanceit.dashboard.utils.toDomainModel
import com.enhanceit.local.persistence.daos.WeatherInfoDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class WeatherInfoPersistenceDataSourceImpl @Inject constructor(
    private val dao: WeatherInfoDao
) : WeatherInfoPersistenceDataSource {
    override suspend fun getWeatherInfoForCity(city: String): StateFlow<WeatherInfo?> {
        return dao.getWeatherForCity(city)
            .distinctUntilChanged()
            .map {
                it?.toDomainModel()
            }.stateIn(CoroutineScope(coroutineContext))
    }

    override suspend fun addWeatherInfo(weatherInfo: WeatherInfo) {
        dao.insert(weatherInfo.fromDomain())
    }

    override suspend fun getWeatherForAll(): StateFlow<List<WeatherInfo>> {
        return dao.getAllWeatherInfo()
            .distinctUntilChanged()
            .map {
                it.toDomainModel()
            }.stateIn(CoroutineScope(coroutineContext))
    }

    override suspend fun deleteWeatherForCity(city: String) {
        dao.delete(city)
    }
}