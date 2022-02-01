package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.Api
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>> get() = _asteroids

    private var asteroidList = ArrayList<Asteroid>()

    init {
        _asteroids.value = asteroidList
    }


     private fun getAsteroids() {
        viewModelScope.launch {
            try {
                val requestCall = Api.retrofitService.getAsteroidsList(Constants.API_KEY,"2022-01-01","2022-01-05")
                requestCall.enqueue(object: Callback<List<Asteroid>>{

                    override fun onResponse(call: Call<List<Asteroid>>, response: Response<List<Asteroid>>) {
                        if (response.isSuccessful) {
                            asteroidList = (response.body() as ArrayList<Asteroid>)
                            _asteroids.value = asteroidList
                        }
                    }

                    override fun onFailure(call: Call<List<Asteroid>>, t: Throwable) {
                        //TODO
                    }
                })
            } catch (e: Exception) { }
        }
    }

    fun apiAsteroidList () {
        getAsteroids()
    }




/**this function only exist to create a list of dummy asteroid*/
    fun dummyAsteroid (adapter : AsteroidAdapter) {
        var id : Long = 0
        for (i in 1..10) {
            val asteroideProva = Asteroid (
                id,
                "ASTEROID $id",
                "DUMMY DATE",
                0.8 + id,
                3.7 + id,
                8.1234 + id,
                9.567 + id,
                false)
            adapter.data.add(asteroideProva)
            id++
        }
    }


}





