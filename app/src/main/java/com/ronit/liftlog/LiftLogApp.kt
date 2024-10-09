package com.ronit.liftlog

import android.app.ActivityManager
import android.app.Application
import android.app.Service
import android.content.Context
import android.util.Log
import com.ronit.liftlog.core.data.mappers.toExerciseList
import com.ronit.liftlog.core.data.remote.ExerciseListService
import com.ronit.liftlog.core.data.repository.ExerciseRepository
import com.ronit.liftlog.core.domain.repository.ExerciseRepositoryImpl
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class LiftLogApp:Application(){

    @Inject
    lateinit var exerciseRepository:ExerciseRepositoryImpl
    @Inject
    lateinit var exerciseListService:ExerciseListService
    override fun onCreate() {
        super.onCreate()
        contex = this




        CoroutineScope(Dispatchers.IO).launch {


            val shouldFetchExercises= exerciseRepository.getAllExerciseCount()


            if(shouldFetchExercises <= 0) {


                val exercises = exerciseListService.getExerciseList().toExerciseList()

                exerciseRepository.upsertAllExercises(exercises)
            }
        }
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