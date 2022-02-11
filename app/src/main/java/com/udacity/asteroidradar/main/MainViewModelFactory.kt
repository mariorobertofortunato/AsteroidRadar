package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**THIS CLASS BECAME USELESS SINCE I USE VIEWMODEL EXTENSION FUNCTIONS:
 * Since you are extending MainViewModel from AndroidViewModel, the extension method will provide
 * the Application instance itself and instantiate it accordingly,
 * this will eliminate the need to have a ViewModelFactory (in this case MainViewModelFactory).*/

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
