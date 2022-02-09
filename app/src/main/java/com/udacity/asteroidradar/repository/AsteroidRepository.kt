package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.ApiAsteroid
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidRoomDatabase
import com.udacity.asteroidradar.database.asDbModel
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AsteroidRepository(private val database: AsteroidRoomDatabase) {

    //trasforma le entities (asteroide di DB) in domainModel (asteroide di app)
    var allAsteroids: LiveData<List<Asteroid>> = Transformations.map(
        database.asteroidDao().getAll()
    ) {
        it.asDomainModel()
    }


    /**API used to refresh offline cache
     */
    suspend fun refreshAsteroidsList() {
        withContext(Dispatchers.IO) {
            try {
                val requestCall = ApiAsteroid.retrofitServiceAsteroid.getAsteroidsList(
                    Constants.API_KEY,
                    getToday(),
                    getSeventhDay()
                )
                requestCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            val asteroidList =
                                parseAsteroidsJsonResult(JSONObject(response.body()!!))
                            database.asteroidDao().insertAll(asteroidList.asDbModel())
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
}


