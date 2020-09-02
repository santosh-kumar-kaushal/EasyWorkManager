package com.library.workmanager

import com.library.workmanager.IBackgroundWorker

/**
 * Task status callbacks.
 */
interface ITaskExecutionCallback: IBackgroundWorker {

    /**
     * Callback when task execution is in progress.
     */
    fun onTaskExecutionInProgress()

    /**
     * Callback when task execution is completed.
     */
    fun onTaskExecutionCompleted()

    /**
     * Callback when task execution is failed.
     */
    fun onTaskExecutionFailed()
}