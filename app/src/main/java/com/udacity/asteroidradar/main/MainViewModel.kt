package com.udacity.asteroidradar.main


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.getDB
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDB(application)
    private val asteroidRepository = AsteroidRepository(database)

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>() //backin property img
    val pictureOfTheDay: LiveData<PictureOfDay> get() = _pictureOfTheDay
    val asteroids = asteroidRepository.allAsteroids
    init {
        viewModelScope.launch {
            refreshAsteroid()
            refreshImage()
        }
    }


    private fun refreshAsteroid() {
        viewModelScope.launch {
            try {
                asteroidRepository.refreshAsteroidsRepository()
            } catch (e: Exception) {
            }
        }
    }

    private fun refreshImage() {
        viewModelScope.launch {
            try {
                _pictureOfTheDay.value = asteroidRepository.refreshImageRepository()
            } catch (e: Exception) {
            }
        }

    }

}
