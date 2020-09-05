package com.sample.easyworkmanager.custom

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.library.workmanager.ITaskExecutionCallback
import com.library.workmanager.WorkType
import com.sample.easyworkmanager.BaseActivity
import com.sample.easyworkmanager.R

class CustomWorkManagerActivity : BaseActivity(), ITaskExecutionCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize your scheduler.
        val ownTaskScheduler = OwnTaskScheduler(
            this,
            this,
            this,
            WorkType.CUSTOM
        )
        // call your request.
        ownTaskScheduler.scheduleCustomRequest()
        viewModel.getData.observe(this, Observer {
            Log.e("MainActivity", it)
        })
    }

    override fun onTaskExecutionInProgress() {
        Log.e("CustMainActivity", "onTaskExecutionInProgress...")
    }

    override fun onTaskExecutionCompleted() {
        Log.e("CustMainActivity", "onTaskExecutionCompleted.")
    }

    override fun onTaskExecutionFailed() {
        Log.e("CustMainActivity", "onTaskExecutionFailed.")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun startBackgroundTask() {
        Log.e("CustMainActivity", "startBackgroundTask.")
        viewModel.fetchDataFromApi()
    }
}