package com.sample.easyworkmanager

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.library.workmanager.ITaskExecutionCallback
import com.library.workmanager.TaskScheduler
import com.library.workmanager.WorkType

class MainActivity : BaseActivity(), ITaskExecutionCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val min = 20 * 60000
        setContentView(R.layout.activity_main)

        //Build Task scheduler with different options.
        TaskScheduler.Builder(this, this).setListener(this).setWorkType(WorkType.ONETIME).build()
        viewModel.getData.observe(this, Observer {
            Log.e("MainActivity", it)
        })

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
        viewModel.fetchDataFromApi()
    }

}