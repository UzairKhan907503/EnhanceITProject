package com.enhanceit.di

import com.enhanceit.navigation.MainActivityNavigation
import com.enhanceit.navigation.MainActivityNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNavigator() : MainActivityNavigation = MainActivityNavigationImpl()
}