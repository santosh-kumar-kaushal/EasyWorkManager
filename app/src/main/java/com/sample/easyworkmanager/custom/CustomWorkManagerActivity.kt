package com.sample.easyworkmanager.custom

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.library.workmanager.ITaskExecutionCallback
import com.library.workmanager.WorkType
import com.sample.easyworkmanager.R
import java.net.HttpURLConnection
import java.net.URL

class CustomWorkManagerActivity:AppCompatActivity(), ITaskExecutionCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize your scheduler.
        val ownTaskScheduler= OwnTaskScheduler(
            this,
            this,
            this,
            WorkType.CUSTOM
        )
        // call your request.
        ownTaskScheduler.scheduleCustomRequest()
    }

    override fun onTaskExecutionInProgress() {
        Log.e("CustMainActivity","onTaskExecutionInProgress...")
    }

    override fun onTaskExecutionCompleted() {
        Log.e("CustMainActivity","onTaskExecutionCompleted.")
    }

    override fun onTaskExecutionFailed() {
        Log.e("CustMainActivity","onTaskExecutionFailed.")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun startBackgroundTask() {
        Log.e("CustMainActivity","startBackgroundTask.")
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
                    Log.e("CustMainActivity",line)
                }
            }
        }
    }
}