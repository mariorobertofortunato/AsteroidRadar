package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/** THIS WHOLE APISERVICE.KT BASICALLY SERVES AS A BUILDER FOR THE Api OBJECT (THE "WAITER"),
 * IN ORDER TO DEFINE ITS FUNCTIONS ( AKA "getAsteroidList" + "getImageOfTheDay").
 *  MOSHI --> RETROFIT --> APISERVICE **/

// Client for modification of connection time-out
private val okHttpClient= OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)
    .build()

//Build the Moshi converter used by Retrofit (KotlinJsonAdapterFactory = from JSON object to Kotlin object)
//This is used for the "Image of the day"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// This interface represent the group of requests that I can operate
// with the Api. The interface defines the method getAsteroidsList
// and getImageOfTheDay (= @GET which returns the url of the pic of the day)
interface ApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidsList(
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ) : String

    @GET("planetary/apod")
    suspend fun getImageOfTheDay(
        @Query("api_key") api_key: String = Constants.API_KEY
    ) : PictureOfDay
}

//The object Network instantiate the retrofit service "containing" the ApiService
//in order to make it accessible to other classes in the project
object Network {

    //Build the Retrofit object for the asteroid (NOT with the Moshi converter, BUT ScalarsConverterFactory)
    private val retrofitAsteroid = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .build()

    //Build the Retrofit object for the image
    private val retrofitImage = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .build()


    val retrofitServiceAsteroid : ApiService by lazy { retrofitAsteroid.create(ApiService::class.java) }
    val retrofitServiceImage : ApiService by lazy { retrofitImage.create(ApiService::class.java) }
}
