package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.network.api.ApiService
import javax.inject.Inject
import javax.inject.Named

class NetworkRepository @Inject constructor(@Named("ServiceImage") private val apiServiceImage: ApiService,
                                            @Named("ServiceAsteroid") private val apiServiceAsteroid : ApiService) {

    suspend fun getAsteroidsList(start_date: String, end_date: String) : String = apiServiceAsteroid.getAsteroidsList(start_date, end_date)

    suspend fun getImageOfTheDay() : PictureOfDay = apiServiceImage.getImageOfTheDay()


}