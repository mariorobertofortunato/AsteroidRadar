package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/** Defines the database configuration and serves as the app's main access point to the persisted data.*/

/** DB holder class*/
@Database (entities = [AsteroidDB::class], version = 1)
abstract class AsteroidRoomDatabase : RoomDatabase() {
    abstract fun asteroidDao(): AsteroidDao
}
    /** Create an instance of the RoomDB with the method getDB*/

    // @Volatile
    private var INSTANCE: AsteroidRoomDatabase? = null        //Declare the instance

    fun getDB(context: Context): AsteroidRoomDatabase {
        return INSTANCE ?: synchronized(AsteroidRoomDatabase::class.java) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AsteroidRoomDatabase::class.java,
                "asteroid_database"
            ).build()
            INSTANCE = instance
            // return instance
            instance
        }
    }


