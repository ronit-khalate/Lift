package com.example.liftlog.start_routine_feature.domain

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.liftlog.LiftLogApp
import com.example.liftlog.start_routine_feature.StartRoutineService
import com.example.liftlog.start_routine_feature.presentation.state.StartRoutineScreenState

object StartRoutineServiceManager {

    private val serviceConnection = object :ServiceConnection{
        override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
            val localBinder =  binder as StartRoutineService.LocalBinder

            serviceBinder = localBinder
            service = localBinder.getService()

            isRunning  = true
            isBound = true

        }

        override fun onServiceDisconnected(p0: ComponentName?) {

            serviceBinder=null
            isBound = false
        }

    }

    var service:StartRoutineService?=null
        private set
    var isRunning by mutableStateOf(false)
        private set
    var isBound = false
        private set


    var routineName by mutableStateOf("")
        private set
    var totalExercises by mutableIntStateOf(0)
        private set
    val state:StartRoutineScreenState?
        get() = service?.state


    val routineId : String?
        get() = service?.routineID



    var serviceBinder: StartRoutineService.LocalBinder? = null
        private set


    // Set the service binder

    fun unbind(){


        if(isBound){


            LiftLogApp.contex.unbindService(serviceConnection)
            serviceBinder=null
            service=null
            isBound=false
        }
    }

    fun setState(state:StartRoutineScreenState){
        service?.let {
            it.setLog(state)
            routineName = state.routine?.name?:""
            totalExercises=state.exercisesLog.size
        }
    }

    fun bind(routineID: String, routineName: String) {




            Intent(LiftLogApp.contex,StartRoutineService::class.java)
                .apply {
                    putExtra(StartRoutineService.ROUTINE_ID,routineID)
                    putExtra(StartRoutineService.ROUTINE_NAME,routineName)
                }
                .also {

                    LiftLogApp.contex.bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
                }



    }

    fun startService(routineID: String,routineName: String){

        val intent = Intent(LiftLogApp.contex,StartRoutineService::class.java).apply {
            putExtra(StartRoutineService.ROUTINE_ID,routineID)
            putExtra(StartRoutineService.ROUTINE_NAME,routineName)
        }
        LiftLogApp.contex.startService(intent)
        isRunning=true
    }

    fun stopService(){


        unbind()
        LiftLogApp.contex.stopService(Intent(LiftLogApp.contex, StartRoutineService::class.java))
        isRunning=false
    }
}