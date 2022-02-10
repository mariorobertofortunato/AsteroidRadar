package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

/** Defines the methods for accessing the DB aka
 * provides the methods that the rest of the app uses to interact with data in the asteroid_db table.*/
@Dao
interface AsteroidDao {

    @Query("SELECT * FROM asteroids_db ORDER BY closeApproachDate ASC")     //prende tutti gli asteroidi già ordinati per closeapproachdate
    fun getAll(): LiveData<List<AsteroidDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroids: List<AsteroidDB>)


}