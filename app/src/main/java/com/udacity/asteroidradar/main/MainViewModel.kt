package com.udacity.asteroidradar.main


import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.ApiImage
import com.udacity.asteroidradar.database.asDomainModel

import com.udacity.asteroidradar.database.getDB
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDB(application)
    private val asteroidRepository = AsteroidRepository(database)

    //trasforma le entities (asteroide di DB) in domainModel (asteroide di app)
    var allAsteroids : LiveData<List<Asteroid>> = Transformations.map(
        database.asteroidDao().getAll()
    ) {
        it.asDomainModel()
    }

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>> get() = _asteroids //backin property asteroids

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>() //backin property img
    val pictureOfTheDay: LiveData<PictureOfDay> get() = _pictureOfTheDay


    private fun refreshAsteroidRepository() {
        viewModelScope.launch {
            try {
                asteroidRepository.refreshAsteroidsList()
            } catch (e: Exception) {
            }
        }
    }


    fun getAsteroidList() {
        refreshAsteroidRepository()
    }

    private fun refreshImageOfTheDay() {
        viewModelScope.launch {
            try {
                val requestCall = ApiImage.retrofitServiceImage.getImageOfTheDay(Constants.API_KEY)
                requestCall.enqueue(object : Callback<PictureOfDay> {
                    override fun onResponse(
                        call: Call<PictureOfDay>,
                        response: Response<PictureOfDay>
                    ) {
                        if (response.body()?.mediaType == "image") {
                            _pictureOfTheDay.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
                        Log.d("TAG_", "An error happened!")
                    }
                })
            } catch (e: Exception) {
            }
        }
    }

    fun getImageOfTheDay() {
        refreshImageOfTheDay()
    }


}





