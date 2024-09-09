package com.example.liftlog.start_routine_feature.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftlog.core.data.model.ExerciseLog
import com.example.liftlog.core.data.model.Set
import com.example.liftlog.core.domain.RealmResponse
import com.example.liftlog.start_routine_feature.data.repository.StartRoutineRepositoryImpl
import com.example.liftlog.start_routine_feature.domain.StartRoutineServiceManager
import com.example.liftlog.start_routine_feature.presentation.event.StartRoutineScreenEvent
import com.example.liftlog.start_routine_feature.presentation.state.StartRoutineScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import java.util.Date

@HiltViewModel(assistedFactory = StartRoutineViewModel.StartRoutineViewModelFactory::class)
class StartRoutineViewModel @AssistedInject constructor(
    @Assisted("routineID") private val routineID:String,
    @Assisted("routineName") val routineName:String,
    private val startRoutineRepositoryImpl: StartRoutineRepositoryImpl
) : ViewModel(){


    private val TAG = "startscreenviewmodel"



    var state by mutableStateOf(StartRoutineScreenState())


    /**
     * if service is null which means  routine just started
     *
     * */

    init {



        viewModelScope.launch {


            if(StartRoutineServiceManager.isRunning) {

                state = StartRoutineServiceManager.service?.state!!
            }
            else {


                when (val response = startRoutineRepositoryImpl.getRoutine(id = ObjectId(hexString = routineID))) {
                    is RealmResponse.Error -> {

                        Log.d(TAG,response.error.message.toString())
                    }
                    is RealmResponse.Success -> {


                        state = state.copy(
                            routine = response.data,
                            date = Date().time,
                            exercisesLog = response.data.exercise.map {
                                ExerciseLog().apply {
                                    this.exercise = it
                                }
                            }.toMutableStateList()
                        )
                    }
                }
            }


        }





    }

    init {

        if (!StartRoutineServiceManager.isRunning) {


            viewModelScope.launch {

                when (val response = startRoutineRepositoryImpl.getLastLogOfRoutineOrNull(
                    routineId = ObjectId(routineID)
                )) {
                    is RealmResponse.Error -> {

                        Log.d(TAG,response.error.message.toString())
                    }
                    is RealmResponse.Success -> {
                        response.data?.let {
                            state = state.copy(
                                lastLog = it.exercisesLog
                            )
                        }

                    }
                }
            }
        }
    }

    // Service Binding
    init {

        if(!StartRoutineServiceManager.isRunning) {


            StartRoutineServiceManager.bind(routineID = routineID , routineName = routineName)
        }



    }

    // get last Routine Log








    fun onEvent(event:StartRoutineScreenEvent){

        when(event){
            is StartRoutineScreenEvent.OnAddSetInExerciseLog ->{

                addSetInExerciseLog(event.id)
            }

            is StartRoutineScreenEvent.OnUpdateWeight -> {



                updateSetProperty(event.id,event.data,event.exLogId){this.weight = it}
            }

            is StartRoutineScreenEvent.OnUpdateReps -> {

                updateSetProperty(event.id,event.data,event.exLogId){this.repetitions = it}

            }

            is StartRoutineScreenEvent.OnUpdateNotes -> {
                updateSetProperty(event.id,event.data,event.exLogId){this.notes = it}
            }

            is StartRoutineScreenEvent.OnRoutineFinish -> {

                StartRoutineServiceManager.setState(state)
                StartRoutineServiceManager.stopService()
            }
        }
    }


    private fun updateSetProperty(setId:String, data:String ,exLogId: String , update: Set.(data:String)-> Unit){
        val exLogIndex = state.exercisesLog.indexOfFirst { it._id.toHexString()== exLogId }

        if (exLogIndex != -1){

            val exLog = state.exercisesLog[exLogIndex]

            val setToBeUpdated = exLog.setList.find { it._id.toHexString() == setId }

            setToBeUpdated?.update(data)

            exLog.setList.removeIf { it._id.toHexString() == setId }

            setToBeUpdated?.let {

                exLog.setList.add(setToBeUpdated)
            }

            state.exercisesLog[exLogIndex] = ExerciseLog().apply {
                this._id = exLog._id
                this.exercise = exLog.exercise
                this.setList = exLog.setList
            }
        }
    }


    private fun addSetInExerciseLog(id: String) {


        val exLogIndex = state.exercisesLog.indexOfFirst { it._id.toHexString()== id }


        if(exLogIndex != -1) {


            val exLogToUpdate = state.exercisesLog[exLogIndex]

            exLogToUpdate.setList.add(Set())
            state.exercisesLog[exLogIndex] = ExerciseLog().apply {
                this._id = exLogToUpdate._id
                this.exercise = exLogToUpdate.exercise
                this.setList = exLogToUpdate.setList
            }


            state = state.copy(exercisesLog = state.exercisesLog)
        }

    }


    // TODO Save state in service
    override fun onCleared() {
        super.onCleared()

        StartRoutineServiceManager.setState(state)





    }





    @AssistedFactory
    interface StartRoutineViewModelFactory{

        fun create(@Assisted("routineID") routineID:String, @Assisted("routineName") routineName: String):StartRoutineViewModel
    }

}