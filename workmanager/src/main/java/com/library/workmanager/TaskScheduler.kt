package com.library.workmanager

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

private const val TAG = "TaskScheduler"

/**
 * Schedules the work based on the constraints provided.
 */
open class TaskScheduler(
    context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val iTaskExecutionCallback: ITaskExecutionCallback,
    private val workType: WorkType = WorkType.ONETIME,
    private val milliseconds: Long = 20 * 60000
) : IBackgroundWorker {

    open var workManager: WorkManager = WorkManager.getInstance(context)

    private lateinit var oneTimeWorkRequest: OneTimeWorkRequest

    private lateinit var periodicWorkRequest: PeriodicWorkRequest

    open lateinit var constraints: Constraints

    private constructor(builder: Builder) : this(
        builder.context, builder.lifecycleOwner,
        builder.iTaskExecutionCallback!!, builder.workType, builder.milliseconds
    )

    init {
        scheduleWork()
    }

    class Builder(
        val context: Context,
        val lifecycleOwner: LifecycleOwner
    ) {
        var iTaskExecutionCallback: ITaskExecutionCallback? = null
        var workType: WorkType = WorkType.ONETIME
        var milliseconds: Long = 5
        fun setListener(iTaskExecutionCallback: ITaskExecutionCallback) =
            apply { this.iTaskExecutionCallback = iTaskExecutionCallback }

        fun setWorkType(workType: WorkType) = apply { this.workType = workType }
        fun setPeriodicTime(milliseconds: Long) = apply { this.milliseconds = milliseconds }
        fun build() = TaskScheduler(this)
    }

    private fun scheduleWork() {
        BackGroundWorker.registerListener(this)
        constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        when (workType) {
            WorkType.ONETIME -> scheduleRequest()
            WorkType.PERIODIC -> scheduleRequest(true)
            WorkType.CUSTOM -> scheduleCustomRequest()
        }

    }

    private fun scheduleRequest(periodic: Boolean = false) {
        val uuid: UUID
        if (periodic) {
            periodicWorkRequest =
                PeriodicWorkRequest.Builder(
                    BackGroundWorker::class.java,
                    milliseconds,
                    TimeUnit.MILLISECONDS
                )
                    .setConstraints(constraints)
                    .build()
            uuid = periodicWorkRequest.id
            workManager.enqueue(periodicWorkRequest)
        } else {
            oneTimeWorkRequest =
                OneTimeWorkRequest.Builder(BackGroundWorker::class.java)
                    .setConstraints(constraints)
                    .build()
            uuid = oneTimeWorkRequest.id
            workManager.enqueue(oneTimeWorkRequest)
        }
        listenToCallbacks(uuid)
    }

    open fun scheduleCustomRequest() {}

    fun listenToCallbacks(uuid: UUID) {
        workManager.getWorkInfoByIdLiveData(uuid)
            .observe(lifecycleOwner, Observer<WorkInfo> { workInfo ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> iTaskExecutionCallback.onTaskExecutionCompleted()
                        WorkInfo.State.FAILED, WorkInfo.State.CANCELLED -> iTaskExecutionCallback.onTaskExecutionFailed()
                        WorkInfo.State.ENQUEUED -> Log.i(TAG, "Task enqueued")
                        WorkInfo.State.RUNNING -> iTaskExecutionCallback.onTaskExecutionInProgress()
                        WorkInfo.State.BLOCKED -> Log.i(TAG, "Task blocked")
                    }
                }
                val refreshTime: String? = workInfo.outputData.getString(REFRESH_TIME)
                Log.i(TAG, "Task refresh time->$refreshTime")
            })

    }

    override fun startBackgroundTask() {
        iTaskExecutionCallback.startBackgroundTask()
    }

}