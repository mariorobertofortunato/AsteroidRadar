package com.udacity.asteroidradar.database

import androidx.room.Database
import androidx.room.RoomDatabase

/** Defines the database configuration and serves as the app's main access point to the persisted data.*/

/** DB holder class*/
@Database (entities = [AsteroidDB::class], version = 1)
abstract class AsteroidRoomDatabase : RoomDatabase() {

    abstract val dao: AsteroidDao

}



