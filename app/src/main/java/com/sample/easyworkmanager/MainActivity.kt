package com.sample.easyworkmanager

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.library.workmanager.ITaskExecutionCallback
import com.library.workmanager.TaskScheduler
import com.library.workmanager.WorkType
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), ITaskExecutionCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val min = 20 * 60000
        setContentView(R.layout.activity_main)

        //Build Task scheduler with different options.
        TaskScheduler.Builder(this, this).setListener(this).setWorkType(WorkType.ONETIME).build()
        
    }

    override fun onTaskExecutionInProgress() {
        Log.e("MainActivity", "onTaskExecutionInProgress...")
    }

    override fun onTaskExecutionCompleted() {
        Log.e("MainActivity", "onTaskExecutionCompleted.")
    }

    override fun onTaskExecutionFailed() {
        Log.e("MainActivity", "onTaskExecutionFailed.")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun startBackgroundTask() {
        Log.e("MainActivity", "startBackgroundTask.")
        fetchDataFromApi()
    }

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
                    Log.e("MainActivity", line)
                }
            }
        }
    }

}