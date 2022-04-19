package com.enhanceit.di.dashboard

import com.enhanceit.dashboard.data.local.datasources.WeatherInfoPersistenceDataSourceImpl
import com.enhanceit.dashboard.data.remote.api.WeatherApiService
import com.enhanceit.dashboard.data.remote.datasources.WeatherInfoRemoteDataSourceImpl
import com.enhanceit.dashboard.domain.datasources.local.WeatherInfoPersistenceDataSource
import com.enhanceit.dashboard.domain.datasources.remote.WeatherInfoRemoteDataSource
import com.enhanceit.dashboard.domain.repository.WeatherRepository
import com.enhanceit.dashboard.domain.repository.WeatherRepositoryImpl
import com.enhanceit.local.persistence.daos.WeatherInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DashboardModule {

    @Provides
    @ViewModelScoped
    fun provideRemoteDataSource(
        dataSourceImpl: WeatherInfoRemoteDataSourceImpl
    ): WeatherInfoRemoteDataSource {
        return dataSourceImpl
    }

    @Provides
    @ViewModelScoped
    fun provideWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ) : WeatherRepository = weatherRepositoryImpl

    @Provides
    @ViewModelScoped
    fun providePersistenceDataSource(
        persistenceDataSourceImpl: WeatherInfoPersistenceDataSourceImpl
    ): WeatherInfoPersistenceDataSource {
        return persistenceDataSourceImpl
    }
}