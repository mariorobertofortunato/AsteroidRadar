package com.udacity.asteroidradar.main


import android.app.Application
import android.view.MenuItem
import androidx.lifecycle.*
import androidx.work.*
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.getDB
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.work.RefreshWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

enum class OptionMenu { SHOW_ALL, SHOW_TODAY, SHOW_WEEK }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDB(application)
    private val asteroidRepository = AsteroidRepository(database)

    private var optionMenu = MutableLiveData(OptionMenu.SHOW_WEEK)                                  // default filter

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
            setupWork()
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

    fun refreshOptionMenu(item: MenuItem){
        when (item.itemId) {
            R.id.show_all_menu -> optionMenu.value = OptionMenu.SHOW_ALL
            R.id.show_today_asteroids -> optionMenu.value =OptionMenu.SHOW_TODAY
            else -> optionMenu.value = OptionMenu.SHOW_WEEK
        }
    }

    private fun setupWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()
        val workRequest = PeriodicWorkRequestBuilder<RefreshWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(getApplication()).enqueueUniquePeriodicWork("work",ExistingPeriodicWorkPolicy.KEEP, workRequest)
    }
}
