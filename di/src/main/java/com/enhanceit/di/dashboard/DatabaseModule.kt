package com.enhanceit.di.dashboard

import android.content.Context
import com.enhanceit.local.persistence.WeatherDataBase
import com.enhanceit.local.persistence.daos.WeatherInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDao(weatherDataBase: WeatherDataBase) : WeatherInfoDao {
        return weatherDataBase.weatherDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): WeatherDataBase {
        return WeatherDataBase.getDatabase(appContext)
    }
}