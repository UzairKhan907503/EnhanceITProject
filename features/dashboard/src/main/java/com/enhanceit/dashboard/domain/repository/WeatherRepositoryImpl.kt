package com.enhanceit.dashboard.domain.repository

import com.enhanceit.core.ext.compareToCurrent
import com.enhanceit.core.utils.dataacessstrategy.cacheFirstStrategy
import com.enhanceit.dashboard.domain.datasources.local.WeatherInfoPersistenceDataSource
import com.enhanceit.dashboard.domain.datasources.remote.WeatherInfoRemoteDataSource
import com.enhanceit.dashboard.data.remote.models.requestmodels.WeatherInputModel
import com.enhanceit.dashboard.domain.models.uimodels.WeatherInfo
import com.enhanceit.remote.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherInfoRemoteDataSource,
    private val persistenceDataSource: WeatherInfoPersistenceDataSource
) : WeatherRepository {

    override suspend fun getWeatherDetails(
        inputModel: WeatherInputModel
    ): Flow<Resource<WeatherInfo>> {
        return cacheFirstStrategy(
            shouldGetFromRemote = { localData ->
                localData?.timestamp?.compareToCurrent() ?: true
            },
            getFromCache = { persistenceDataSource.getWeatherInfoForCity(inputModel.city) },
            getFromRemote = { remoteDataSource.getWeatherInfoForCity(inputModel) },
            updateCache = { updateCache(it) }
        )
    }

    override suspend fun getAllWeathers(): Flow<List<WeatherInfo>> {
        return persistenceDataSource.getWeatherForAll()
    }


    private suspend fun updateCache(weatherInfo: WeatherInfo) {
        persistenceDataSource.deleteWeatherForCity(weatherInfo.cityName)
        persistenceDataSource.addWeatherInfo(weatherInfo)
    }

}