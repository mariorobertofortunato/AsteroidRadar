package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.network.getSeventhDay
import com.udacity.asteroidradar.network.getToday
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidRoomDatabase
import com.udacity.asteroidradar.database.asDBModel
import com.udacity.asteroidradar.database.asDomainModel
import org.json.JSONObject
import javax.inject.Inject

class MainRepository @Inject constructor (private val databaseRepository: DBRepository,
                                          private val networkRepository: NetworkRepository) {

    var allAsteroids: LiveData<List<Asteroid>> = Transformations.map(databaseRepository.getAll())
    { it.asDomainModel() }

    var todayAsteroids: LiveData<List<Asteroid>> = Transformations.map(databaseRepository.getToday(getToday()))
    { it.asDomainModel() }

    var weekAsteroids: LiveData<List<Asteroid>> = Transformations.map(databaseRepository.getWeek(getToday(), getSeventhDay()))
    { it.asDomainModel() }


    /**API used to refresh offline cache (= the DB)*/
    suspend fun refreshAsteroidsRepository() {
        try {
            val response = networkRepository.getAsteroidsList(
                getToday(),
                getSeventhDay()
            )
            val asteroidFromNetwork = parseAsteroidsJsonResult(JSONObject(response))
            databaseRepository.insertAll(asteroidFromNetwork.asDBModel())
        } catch (e: Exception) {
        }
    }

    suspend fun refreshImageRepository(): PictureOfDay {
        val imgPlaceHolder = PictureOfDay(
            "nonImg",
            "PlaceHolder",
            "https://apod.nasa.gov/apod/image/2201/MoonstripsAnnotatedIG_crop1024.jpg"          //this is a random img used as a placeholder in case of a video
        )
        val responseImg = networkRepository.getImageOfTheDay()
        if (responseImg.mediaType != "image") {
            return imgPlaceHolder
        }
        return responseImg
    }


}


