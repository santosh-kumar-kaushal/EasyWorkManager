package com.sample.easyworkmanager.custom

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import com.library.workmanager.BackGroundWorker
import com.library.workmanager.ITaskExecutionCallback
import com.library.workmanager.TaskScheduler
import com.library.workmanager.WorkType

/**
 * Your own custom scheduler which can have other constraints and other type of work request.
 * Just extend {TaskScheduler class}.
 */
class OwnTaskScheduler(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    iTaskExecutionCallback: ITaskExecutionCallback,
    workType: WorkType
) : TaskScheduler(context, lifecycleOwner, iTaskExecutionCallback, workType) {

    private lateinit var oneTimeWorkRequest: OneTimeWorkRequest

    //Your own constraints.
    override var constraints: Constraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    //Your own request type.
    override fun scheduleCustomRequest() {
        oneTimeWorkRequest =
            OneTimeWorkRequest.Builder(BackGroundWorker::class.java)
                .setConstraints(constraints)
                .build()
        workManager.enqueue(oneTimeWorkRequest)
        //Set the callbacks to listen to changes.
        listenToCallbacks(oneTimeWorkRequest.id)
    }

}