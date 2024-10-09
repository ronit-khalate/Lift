package com.ronit.liftlog.start_routine_feature.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronit.liftlog.core.data.model.entity.ExerciseLog
import com.ronit.liftlog.core.data.model.entity.Set
import com.ronit.liftlog.core.domain.RealmResponse
import com.ronit.liftlog.core.domain.toEpochMillis
import com.ronit.liftlog.start_routine_feature.data.model.ExerciseLogDto
import com.ronit.liftlog.start_routine_feature.data.model.SetDto
import com.ronit.liftlog.start_routine_feature.data.repository.StartRoutineRepositoryImpl
import com.ronit.liftlog.start_routine_feature.domain.StartRoutineServiceManager
import com.ronit.liftlog.start_routine_feature.presentation.event.StartRoutineScreenEvent
import com.ronit.liftlog.start_routine_feature.presentation.state.StartRoutineScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

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
                            date = LocalDate.now().toEpochMillis(),
                            exercisesLog = response.data.exercise.map {

                                ExerciseLogDto(
                                    exerciseID = it._id.toHexString(),
                                    name = it.name,
                                    muscleGroup = it.muscleGroup?:"",
                                    note = ""


                                )
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


                        val previousExerciseLog = response.data?.exercisesLog
                        val currentExerciseLog = state.exercisesLog

                        if(previousExerciseLog != null) {


                            currentExerciseLog.zip(previousExerciseLog).forEach { pair: Pair<ExerciseLogDto, ExerciseLog> ->


                                pair.second.setList.sortedBy { it.setNo }.forEach {preSet: Set ->

                                    pair.first.setList.add(
                                        SetDto(
                                            setNo = preSet.setNo,
                                            exerciseId = pair.first.exerciseID,
                                            prevWeight = preSet.weight,
                                            prevRepetitions = preSet.repetitions,
                                            prevNotes = preSet.notes
                                        )
                                    )
                                }


                            }
                        }

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



                updateSetProperty(event.id,event.data,event.exLogId){

                    this.copy(weight = it)
                }
            }

            is StartRoutineScreenEvent.OnUpdateReps -> {

                updateSetProperty(event.id,event.data,event.exLogId){this.copy(repetitions = it)}

            }

            is StartRoutineScreenEvent.OnUpdateNotes -> {
                updateSetProperty(event.id,event.data,event.exLogId){this.copy(notes = it)}
            }

            is StartRoutineScreenEvent.OnRoutineFinish -> {

                StartRoutineServiceManager.setState(state)
                StartRoutineServiceManager.stopService()
            }
        }
    }


    private fun updateSetProperty(setId:String, data:String ,exLogId: String , update: SetDto.(data:String)-> SetDto){
        val exLogIndex = state.exercisesLog.indexOfFirst { it.id.toHexString()== exLogId }

        if (exLogIndex != -1){

            val exLog = state.exercisesLog[exLogIndex]

            val setToBeUpdatedIndex = exLog.setList.indexOfFirst { it.id.toHexString() == setId }


            if(setToBeUpdatedIndex != -1){

                var setToBeUpdated = exLog.setList[setToBeUpdatedIndex]
                setToBeUpdated = setToBeUpdated.update(data)

                exLog.setList[setToBeUpdatedIndex] = setToBeUpdated
            }

            state.exercisesLog[exLogIndex] =exLog.copy()
        }
    }


    private fun addSetInExerciseLog(id: String) {


        val exLogIndex = state.exercisesLog.indexOfFirst { it.id.toHexString()== id }


        if(exLogIndex != -1) {


            val exLogToUpdate = state.exercisesLog[exLogIndex]

            exLogToUpdate.setList.add(
                SetDto(
                    exerciseId = exLogToUpdate.exerciseID,
                )
            )
            state.exercisesLog[exLogIndex] = exLogToUpdate.copy(setList = exLogToUpdate.setList)


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