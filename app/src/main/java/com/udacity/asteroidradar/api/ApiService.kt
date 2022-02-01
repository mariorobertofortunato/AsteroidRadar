package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/** THIS WHOLE APISERVICE.KT BASICALLY SERVES AS A BUILDER FOR THE Api OBJECT (THE "WAITER"),
 * IN ORDER TO DEFINE ITS FUNCIONS ( AKA "getAsteroidList").
 *  MOSHI --> RETROFIT --> APISERVICE **/

//Build the Moshi converter used by Retrofit (KotlinJsonAdapterFactory = from JSON object to Kotlin object)
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Build the Retrofit object (with the Moshi converter built above)
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

// This interface represent the group of requests that I can operate
// with the Api. In this case the interface defines the method getAsteroidsList
// (= @GET which returns the list of asteroids)
interface ApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidsList(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ) : Call<List<Asteroid>>
}

//The object Api instantiate the retrofit service "containing" the ApiService created above
//in order to make it accessible to other classes in the project
object Api {
    val retrofitService : ApiService by lazy { retrofit.create(ApiService::class.java) }
}
