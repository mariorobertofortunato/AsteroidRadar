package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/** Defines the methods for accessing the DB aka
 * provides the methods that the rest of the app uses to interact with data in the asteroid_db table.*/
@Dao
interface AsteroidDao {

    @Query("SELECT * FROM asteroids_db ORDER BY closeApproachDate ASC")
    fun getAll(): LiveData<List<AsteroidDB>>

    @Query("SELECT * FROM asteroids_db WHERE closeApproachDate = (:today)")
    fun getToday(today: String) : LiveData<List<AsteroidDB>>

    @Query("SELECT * from asteroids_db where closeApproachDate between (:today) and (:seventhDay) order by closeApproachDate asc")
    fun getWeek(today: String, seventhDay: String) : LiveData<List<AsteroidDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroids: List<AsteroidDB>)


}