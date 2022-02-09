package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/** THIS WHOLE APISERVICE.KT BASICALLY SERVES AS A BUILDER FOR THE Api OBJECT (THE "WAITER"),
 * IN ORDER TO DEFINE ITS FUNCTIONS ( AKA "getAsteroidList" + "getImageOfTheDay").
 *  MOSHI --> RETROFIT --> APISERVICEASTEROID / APISERVICEIMAGE **/


/** API FOR THE ASTEROID LIST*/
//Build the Retrofit object for the asteroid (NOT with the Moshi converter, BUT ScalarsConverterFactory)
//APPARENTLY  if you use Moshi as a converter for dynamic JSON keys (or in combination?) it just won't work, it kinda blocks the call.enqueue method, fuck knows why
private val retrofitAsteroid = Retrofit.Builder()
    //.addConverterFactory(MoshiConverterFactory.create(moshi))
    .addConverterFactory(ScalarsConverterFactory.create())  //this is used for strings =  the data contains dynamic JSON keys and we cannot use Moshi to directly map the response to data class
    .baseUrl(Constants.BASE_URL)
    .build()

// This interface represent the group of requests that I can operate
// with the Api for the asteroid. In this case the interface defines the method getAsteroidsList
// (= @GET which returns the list of asteroids)
interface ApiServiceAsteroid {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidsList(
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ) : String
}

//The object ApiAsteroid instantiate the retrofit service "containing" the ApiServiceAsteroid created above
//in order to make it accessible to other classes in the project
object ApiAsteroid {
    val retrofitServiceAsteroid : ApiServiceAsteroid by lazy { retrofitAsteroid.create(ApiServiceAsteroid::class.java) }
}

/**API FOR THE IMAGE*/
//Build the Moshi converter used by Retrofit (KotlinJsonAdapterFactory = from JSON object to Kotlin object)
//This is used for the "Image of the day"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Build the Retrofit object for the image
private val retrofitImage = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

// This interface represent the group of requests that I can operate
// with the Api for the image of the day.
interface ApiServiceImage {
    @GET("planetary/apod")
    fun getImageOfTheDay(
        @Query("api_key") api_key: String = Constants.API_KEY
    ) : Call<PictureOfDay>
}

//The object ApiImage instantiate the retrofit service "containing" the ApiServiceImage created above
//in order to make the function accessible to other classes in the project
object ApiImage {
    val retrofitServiceImage : ApiServiceImage by lazy { retrofitImage.create(ApiServiceImage::class.java) }
}


