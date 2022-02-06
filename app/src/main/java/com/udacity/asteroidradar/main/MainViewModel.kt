package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>> get() = _asteroids //backin property asteroids
    private var asteroidList = ArrayList<Asteroid>()

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>() //backin property img
    val pictureOfTheDay: LiveData<PictureOfDay> get() = _pictureOfTheDay


    private fun refreshAsteroidList() {
        viewModelScope.launch {
            try {
                val requestCall = ApiAsteroid.retrofitServiceAsteroid.getAsteroidsList(Constants.API_KEY, getToday(), getSeventhDay())
                requestCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            asteroidList = parseAsteroidsJsonResult(JSONObject(response.body()!!)) as ArrayList<Asteroid>
                            _asteroids.value = asteroidList
                        }
                    }
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("TAG_", "An error happened!")
                    }
                })
            } catch (e: Exception) {
            }
        }
    }

    fun getAsteroidList () {
        refreshAsteroidList()
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





