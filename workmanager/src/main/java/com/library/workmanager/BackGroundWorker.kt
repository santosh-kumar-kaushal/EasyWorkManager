package com.library.workmanager

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

const val REFRESH_TIME = "refresh-time"

/**
 * Background worker class to execute background operation.
 */
class BackGroundWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        private lateinit var iBackgroundWorker: IBackgroundWorker
        fun registerListener(iBackgroundWorker: IBackgroundWorker) {
            this.iBackgroundWorker = iBackgroundWorker
        }
    }

    override fun doWork(): Result {
        iBackgroundWorker.startBackgroundTask()
        val outputData: Data =
            Data.Builder().putString(REFRESH_TIME, "${System.currentTimeMillis()}").build()
        return Result.success(outputData)
    }
}