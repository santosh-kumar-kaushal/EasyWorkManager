package com.sample.easyworkmanager

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.net.HttpURLConnection
import java.net.URL

class ActivityViewModel : ViewModel() {


    val getData: LiveData<String> get() = _getData
    private val _getData = MutableLiveData<String>()
    /**
     * Sample api call needs to be called on @startBackgroundTask().
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun fetchDataFromApi() {
        val url = URL("http://www.google.com/")

        with(url.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "GET"

            println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")

            inputStream.bufferedReader().use {
                //To use this we @RequiresApi(Build.VERSION_CODES.N)
                it.lines().forEach { line ->
                    _getData.postValue(line)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ActivityViewModel() as T
        }
    }


}