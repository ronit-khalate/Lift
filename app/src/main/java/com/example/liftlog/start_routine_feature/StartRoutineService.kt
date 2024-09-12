package com.example.liftlog.start_routine_feature

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log.*

import com.example.liftlog.start_routine_feature.data.repository.StartRoutineRepositoryImpl
import com.example.liftlog.start_routine_feature.presentation.state.StartRoutineScreenState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StartRoutineService:Service() {


    @Inject
    lateinit var startRoutineRepositoryImpl: StartRoutineRepositoryImpl

    private val TAG = "start_routine"
    private val binder = LocalBinder()

    var routineName:String? = null
        private set
    var routineID:String? = null
        private set

    var state:StartRoutineScreenState?=null
        private set


    fun setLog(state: StartRoutineScreenState){

        this.state = state
    }

    override fun onBind(intent: Intent?): IBinder? {

        CoroutineScope(Dispatchers.Main).launch{

            var count=0
            while(this.isActive){

                d(TAG,count.toString())
                count++
                delay(1000L)
            }
        }
        routineName = intent?.getStringExtra(ROUTINE_NAME)
        routineID = intent?.getStringExtra(ROUTINE_ID)
        return binder
    }

    var num=0;




    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        CoroutineScope(Dispatchers.Main).launch{

            var count=0
            while(this.isActive){

                d(TAG,count.toString())
                count++
                delay(1000L)
            }
        }

            routineName = intent?.getStringExtra(ROUTINE_NAME)
            routineID = intent?.getStringExtra(ROUTINE_ID)


        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

       stopSelf()
    }

    override fun onUnbind(intent: Intent?): Boolean {


        CoroutineScope(Dispatchers.Default).launch{

            state?.let {
                startRoutineRepositoryImpl.saveLog(it)
            }
        }

        return super.onUnbind(intent)

    }





    inner class LocalBinder: Binder(){

        fun getService():StartRoutineService = this@StartRoutineService
    }
    companion object{

        const val ROUTINE_NAME = "routine_name"
        const val ROUTINE_ID ="routine_id"
    }
}