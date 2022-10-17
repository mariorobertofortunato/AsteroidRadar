package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.database.AsteroidDB
import com.udacity.asteroidradar.database.AsteroidRoomDatabase
import javax.inject.Inject

class DBRepository @Inject constructor(private val database: AsteroidRoomDatabase) {

    fun getAll(): LiveData<List<AsteroidDB>> = database.dao.getAll()

    fun getToday(today: String) : LiveData<List<AsteroidDB>> = database.dao.getToday(today)

    fun getWeek(today: String, seventhDay: String) : LiveData<List<AsteroidDB>> = database.dao.getWeek(today,seventhDay)

    suspend fun insertAll(asteroids: List<AsteroidDB>) = database.dao.insertAll(asteroids)

    fun deleteOldAsteroids(today: String) = database.dao.deleteOldAsteroids(today)
}