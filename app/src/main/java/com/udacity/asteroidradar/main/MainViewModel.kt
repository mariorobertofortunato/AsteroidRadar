package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.Api
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>> get() = _asteroids

    private var asteroidList = ArrayList<Asteroid>()

    private fun getAsteroids() {
        viewModelScope.launch {
            try {
                val requestCall = Api.retrofitService.getAsteroidsList(Constants.API_KEY, getToday(), getSeventhDay())
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

    fun apiAsteroidList () {
        getAsteroids()
    }




}





