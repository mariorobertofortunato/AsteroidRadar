package com.udacity.asteroidradar.main



import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.ApiImage

import com.udacity.asteroidradar.database.getDB
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // THIS LINE MAKES THE APP CRASH
   //val asteroidRepository = AsteroidRepository(getDB(application))

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>> get() = _asteroids //backin property asteroids
    private var asteroidList = ArrayList<Asteroid>()

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>() //backin property img
    val pictureOfTheDay: LiveData<PictureOfDay> get() = _pictureOfTheDay


    private fun refreshAsteroidRepository() {
        viewModelScope.launch {
            try {
                //asteroidRepository.refreshAsteroidsList()
            } catch (e: Exception) {
            }
        }
    }

    init {
        refreshAsteroidRepository()
    }

    fun getAsteroidList () {
        refreshAsteroidRepository()
    }

    private fun refreshImageOfTheDay() {
        viewModelScope.launch {
            try {
                val requestCall = ApiImage.retrofitServiceImage.getImageOfTheDay(Constants.API_KEY)
                requestCall.enqueue(object : Callback<PictureOfDay> {
                    override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
                        if (response.body()?.mediaType=="image") {
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

    fun getImageOfTheDay () {
        refreshImageOfTheDay()
    }





}





