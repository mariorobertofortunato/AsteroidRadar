package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidRoomDatabase
import com.udacity.asteroidradar.database.asDBModel
import com.udacity.asteroidradar.database.asDomainModel
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidRoomDatabase) {

    var allAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAll())
    { it.asDomainModel() }

    var todayAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getToday(getToday()))
    { it.asDomainModel() }

    var weekAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getWeek(getToday(), getSeventhDay()))
    { it.asDomainModel() }


    /**API used to refresh offline cache (= the DB)*/
    suspend fun refreshAsteroidsRepository() {
        try {
            val response = Network.retrofitServiceAsteroid.getAsteroidsList(
                getToday(),
                getSeventhDay()
            )
            val asteroidFromNetwork = parseAsteroidsJsonResult(JSONObject(response))
            database.asteroidDao.insertAll(asteroidFromNetwork.asDBModel())
        } catch (e: Exception) {
        }
    }

    suspend fun refreshImageRepository(): PictureOfDay {
        val imgPlaceHolder = PictureOfDay(
            "nonImg",
            "PlaceHolder",
            "https://apod.nasa.gov/apod/image/2201/MoonstripsAnnotatedIG_crop1024.jpg"          //this is a random img used as a placeholder in case of a video
        )
        val responseImg = Network.retrofitServiceImage.getImageOfTheDay()
        if (responseImg.mediaType != "image") {
            return imgPlaceHolder
        }
        return responseImg
    }


}


