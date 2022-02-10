package com.udacity.asteroidradar.main


import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.getDB
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

enum class OptionMenu { SHOW_ALL, SHOW_TODAY, SHOW_WEEK }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDB(application)
    private val asteroidRepository = AsteroidRepository(database)

    var optionMenu = MutableLiveData(OptionMenu.SHOW_WEEK)                                          // default filter

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()                                  //backin property img
    val pictureOfTheDay: LiveData<PictureOfDay> get() = _pictureOfTheDay

    // switch case for defining the observable asteroids var depending on the option menu value (aka filter)
    val asteroids = Transformations.switchMap(optionMenu){
        when (it){
            OptionMenu.SHOW_ALL -> asteroidRepository.allAsteroids
            OptionMenu.SHOW_TODAY -> asteroidRepository.todayAsteroids
            else -> asteroidRepository.weekAsteroids
        }
    }

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
