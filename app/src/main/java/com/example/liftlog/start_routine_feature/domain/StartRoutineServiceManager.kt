package com.example.liftlog.start_routine_feature.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.liftlog.start_routine_feature.StartRoutineService

object StartRoutineServiceManager {

    private var isRunning by mutableStateOf(false)
    private var serviceBinder: StartRoutineService.LocalBinder? = null

    // Check if the service is running
    fun isServiceRunning(): Boolean = isRunning

    // Set the service running state
    fun setServiceRunning(running: Boolean) {
        isRunning = running
    }

    // Get the service binder if available
    fun getServiceBinder(): StartRoutineService.LocalBinder? = serviceBinder

    // Set the service binder
    fun setServiceBinder(binder: StartRoutineService.LocalBinder?) {
        serviceBinder = binder
    }

    fun stopService(){

        serviceBinder?.getService()?.stopSelf()
        isRunning=false
        serviceBinder = null
    }
}