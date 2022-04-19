package com.enhanceit.local.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enhanceit.local.persistence.daos.WeatherInfoDao
import com.enhanceit.local.persistence.models.WeatherEntity

private const val DB_NAME = "weatherDatabase"

@Database(entities = [WeatherEntity::class], version = 1)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherInfoDao

    companion object {
        @Volatile
        private var instance: WeatherDataBase? = null

        fun getDatabase(context: Context): WeatherDataBase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, WeatherDataBase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}