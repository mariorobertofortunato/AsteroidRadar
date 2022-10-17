package com.udacity.asteroidradar.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

//Defines the unit of work
class RefreshWorker (appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams)  {

    //TODO commentato per il momento per non rompere il cazzo, errore sul costruttore del repository

    override suspend fun doWork(): Result {
       //val database = getDB(applicationContext)
        //val repository = AsteroidRepository(database)
        return try {
            //repository.refreshAsteroidsRepository()
           // repository.refreshImageRepository()
            Log.d("TAG", "doWork TRY")
            Result.success()
        } catch (e: Exception) {
            Log.d("TAG", "doWork Catch")
            Result.retry()
        }

    }

}