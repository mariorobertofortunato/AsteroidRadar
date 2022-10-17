package com.udacity.asteroidradar.network.api

import com.udacity.asteroidradar.model.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidsList(
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ) : String

    @GET("planetary/apod")
    suspend fun getImageOfTheDay() : PictureOfDay


}

