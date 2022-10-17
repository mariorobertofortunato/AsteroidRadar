package com.udacity.asteroidradar.di.module

import android.content.Context
import androidx.room.Room
import com.udacity.asteroidradar.database.AsteroidRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): AsteroidRoomDatabase =
        Room
            .databaseBuilder(
                context.applicationContext,
                AsteroidRoomDatabase::class.java,
                "asteroids"
            )
            .build()
}