package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.ApiAsteroid
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidRoomDatabase
import com.udacity.asteroidradar.database.asDbModel
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidRoomDatabase) {

    /**API used to refresh offline cache
     */
    suspend fun refreshAsteroidsList() {

        try {
            val response = ApiAsteroid.retrofitServiceAsteroid.getAsteroidsList(
                Constants.API_KEY,
                getToday(),
                getSeventhDay()
            )
            val asteroidList =
                parseAsteroidsJsonResult(JSONObject(response))
            database.asteroidDao().insertAll(asteroidList.asDbModel())
        } catch (e: Exception) {
        }

    }
}


