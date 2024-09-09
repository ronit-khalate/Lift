package com.example.liftlog

import android.app.ActivityManager
import android.app.Application
import android.app.Service
import android.content.Context
import android.content.ServiceConnection
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LiftLogApp:Application(){

    override fun onCreate() {
        super.onCreate()
        contex = this
    }




    companion object{
        fun isServiceRunning(context: Context, serviceClass: Class<out Service>): Boolean {
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val runningServices = activityManager.getRunningServices(Int.MAX_VALUE)

            for (service in runningServices) {

                Log.d("_app_",service.service.className)
                if (service.service.className == serviceClass.name) {
                    return true
                }
            }
            return false
        }
        lateinit var contex:Context
            private set
    }
}