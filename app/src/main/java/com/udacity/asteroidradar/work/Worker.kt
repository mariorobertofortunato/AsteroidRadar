package com.udacity.asteroidradar.work

import android.content.Context
import android.util.Log
import androidx.work.*
import com.udacity.asteroidradar.database.getDB
import com.udacity.asteroidradar.repository.AsteroidRepository
import java.lang.Exception

//Defines the unit of work
class RefreshWorker (appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams)  {

    override suspend fun doWork(): Result {
        val database = getDB(applicationContext)
        val repository = AsteroidRepository(database)
        return try {
            repository.refreshAsteroidsRepository()
            repository.refreshImageRepository()
            Log.d("TAG", "doWork TRY")
            Result.success()
        } catch (e: Exception) {
            Log.d("TAG", "doWork Catch")
            Result.retry()
        }
    }
}