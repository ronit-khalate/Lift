package com.example.liftlog.start_routine_feature

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.example.liftlog.core.data.mappers.toLog
import com.example.liftlog.core.data.model.Log
import com.example.liftlog.start_routine_feature.data.repository.StartRoutineRepositoryImpl
import com.example.liftlog.start_routine_feature.presentation.state.StartRoutineScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import javax.inject.Inject

class StartRoutineService:Service() {


    @Inject
    lateinit var startRoutineRepositoryImpl: StartRoutineRepositoryImpl

    private val TAG = "start_routine"
    private val binder = LocalBinder()

    var routineName:String? = null
        private set

    var state:StartRoutineScreenState?=null
        private set
    override fun onBind(p0: Intent?): IBinder? {

        return binder
    }

    var num=0;



    fun setLog(state: StartRoutineScreenState){

        this.state = state
    }

    inner class LocalBinder: Binder(){

        fun getService():StartRoutineService = this@StartRoutineService
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {



            routineName = intent?.getStringExtra(ROUTINE_NAME)


        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        CoroutineScope(Dispatchers.Unconfined).launch{

            state?.let {
                startRoutineRepositoryImpl.saveLog(it.toLog())
            }

        }
    }



    companion object{

        const val ROUTINE_NAME = "routine_name"
    }
}