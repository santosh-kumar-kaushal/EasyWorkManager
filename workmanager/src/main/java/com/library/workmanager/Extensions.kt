package com.library.workmanager

import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import java.util.*

fun UUID.getID(periodicWorkRequest:PeriodicWorkRequest):UUID=periodicWorkRequest.id

fun UUID.getID(oneTimeWorkRequest: OneTimeWorkRequest):UUID=oneTimeWorkRequest.id