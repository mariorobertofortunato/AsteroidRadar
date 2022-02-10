package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/** Defines the database configuration and serves as the app's main access point to the persisted data.*/

/** DB holder class*/
@Database (entities = [AsteroidDB::class], version = 1)
abstract class AsteroidRoomDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}
    /** Create an instance of the RoomDB with the method getDB*/

    private lateinit var INSTANCE: AsteroidRoomDatabase

    fun getDB(context: Context): AsteroidRoomDatabase {
        synchronized(AsteroidRoomDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room
                    .databaseBuilder(context.applicationContext, AsteroidRoomDatabase::class.java, "asteroids")
                    .build()
            }
        }
        return INSTANCE
    }


