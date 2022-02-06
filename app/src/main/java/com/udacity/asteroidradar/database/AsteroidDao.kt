package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/** Defines the methods for accessing the DB aka
 * provides the methods that the rest of the app uses to interact with data in the asteroid_db table.*/
@Dao
interface AsteroidDao {

    @Query("SELECT * FROM asteroids_db")
    fun getAll(): List<Asteroid>

    /*@Query("SELECT * FROM asteroids_db WHERE id IN (:Asteroid)")
    fun loadAllByIds(userIds: IntArray): List<Asteroid>*/

    @Insert
    fun insertAll(vararg allAsteroids: Asteroid)

    @Delete
    fun delete(asteroid: Asteroid)
}