package com.udacity.asteroidradar.main



import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
<<<<<<< HEAD
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.ApiImage
import com.udacity.asteroidradar.database.getDB
import com.udacity.asteroidradar.repository.AsteroidRepository
=======
import com.udacity.asteroidradar.api.Api
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
>>>>>>> parent of bf0a2f7 (Don't know why, but it fuckin works now)
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // THIS LINE MAKES THE APP CRASH
    val asteroidRepository = AsteroidRepository(getDB(application))


    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>> get() = _asteroids

    private var asteroidList = ArrayList<Asteroid>()

<<<<<<< HEAD
    private fun refreshAsteroidRepository() {
        viewModelScope.launch {
            try {
                //asteroidRepository.refreshAsteroidsList()
=======
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
>>>>>>> parent of bf0a2f7 (Don't know why, but it fuckin works now)
            } catch (e: Exception) {
            }
        }

<<<<<<< HEAD
    init {
        refreshAsteroidRepository()
    }

    fun getAsteroidList () {
        refreshAsteroidRepository()
    }
=======
>>>>>>> parent of bf0a2f7 (Don't know why, but it fuckin works now)

    }

<<<<<<< HEAD
    fun getImageOfTheDay () {
        refreshImageOfTheDay()
=======
    fun apiAsteroidList () {
        getAsteroids()
>>>>>>> parent of bf0a2f7 (Don't know why, but it fuckin works now)
    }




<<<<<<< HEAD

=======
>>>>>>> parent of bf0a2f7 (Don't know why, but it fuckin works now)
}





