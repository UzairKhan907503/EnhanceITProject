package com.enhanceit.local.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enhanceit.local.persistence.models.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherInfoDao {
    @Query("SELECT * FROM WeatherEntity WHERE cityName LIKE '%' || :city || '%'")
    fun getWeatherForCity(city: String): Flow<WeatherEntity?>

    @Query("SELECT * FROM WeatherEntity")
    fun getAllWeatherInfo(): Flow<List<WeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: WeatherEntity)

    @Query("DELETE FROM WeatherEntity WHERE cityName = :city")
    suspend fun delete(city : String)
}