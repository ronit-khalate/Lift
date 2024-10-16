package com.ronit.liftlog.start_routine_feature.presentation

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronit.liftlog.core.data.model.entity.ExerciseLog
import com.ronit.liftlog.core.data.model.entity.Set
import com.ronit.liftlog.core.data.model.entity.Workout
import com.ronit.liftlog.core.domain.RealmResponse
import com.ronit.liftlog.core.domain.repository.ExerciseRepositoryImpl
import com.ronit.liftlog.core.domain.repository.WorkoutRepositoryImpl
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
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

@HiltViewModel(assistedFactory = StartRoutineViewModel.StartRoutineViewModelFactory::class)
class StartRoutineViewModel @AssistedInject constructor(
    @Assisted("routineID") private val routineID:String,
    @Assisted("routineName") val routineName:String,
    private val startRoutineRepo: StartRoutineRepositoryImpl,
    private val exerciseRepo:ExerciseRepositoryImpl,
    private val workoutRepo: WorkoutRepositoryImpl
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


                when (val response = startRoutineRepo.getRoutine(id = ObjectId(hexString = routineID))) {
                    is RealmResponse.Error -> {

                        Log.d(TAG,response.error.message.toString())
                    }
                    is RealmResponse.Success -> {


                        state = state.copy(
                            routine = response.data,
                            date = LocalDate.now().toEpochMillis(),
                            workouts = exerciseRepo.getExercises(response.data.exerciseIds)
                                .map {exercise ->

                                    val prevSet =workoutRepo.getLatestWorkoutOfExercise(exercise._id)?.sets?: emptyList()
                                    Workout().apply {
                                        this.exerciseId = exercise._id

//                                        this.previousSets =
                                        this.sets =  realmListOf(

                                                *prevSet.map {
                                                    Set().apply {
                                                        this.setNo= it.setNo
                                                        this.previousWeight = it.weight
                                                        this.previousRepetitions =it.repetitions
                                                        this.previousNote = it.notes
                                                    }
                                                }.toTypedArray()
                                        )
                                        this.exerciseName = exercise.name


                                    }
                                }
                                .toMutableStateList()


                        )
                        StartRoutineServiceManager.setState(state)
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


    fun onEvent(event:StartRoutineScreenEvent){

        when(event){
            is StartRoutineScreenEvent.OnAddSetInExerciseLog ->{

                addSetInExerciseLog(event.id)
            }

            is StartRoutineScreenEvent.OnUpdateWeight -> {



                updateSetProperty(workoutId = event.workoutId, data = event.data){data->

                    this.updateSet(
                        setId = event.setId,
                        update = { this.copyData(weight = event.data)}
                    )
                }
            }

            is StartRoutineScreenEvent.OnUpdateReps -> {

                updateSetProperty(workoutId = event.workoutId, data = event.data){data->

                    this.updateSet(
                        setId =  event.setId,
                        update = { this.copyData(repetitions = event.data)}
                    )
                }

            }

            is StartRoutineScreenEvent.OnUpdateNotes -> {
                updateSetProperty(workoutId = event.workoutId, data = event.data){data->

                    this.updateSet(
                        setId = event.setId,
                        update = {this.copyData(notes = event.data)}
                    )
                }
            }

            is StartRoutineScreenEvent.OnRoutineFinish -> {

                StartRoutineServiceManager.setState(state)
                StartRoutineServiceManager.stopService()
            }

            is StartRoutineScreenEvent.OnBodyWeightEntered -> {

                state= state.copy(bodyWeight = event.weight)
            }
        }
    }


    private fun updateSetProperty(workoutId:ObjectId, data:String , update: Workout.(data:String)-> Workout){




        val idOfWorkoutToBeUpdate = state.workouts.indexOfFirst { it._id == workoutId }



        if(idOfWorkoutToBeUpdate != -1){

            state.workouts[idOfWorkoutToBeUpdate] = state.workouts[idOfWorkoutToBeUpdate].update(data)
        }


    }


    private fun addSetInExerciseLog(workoutId: String) {


        val indexOfWorkout = state.workouts.indexOfFirst { it._id.toHexString() == workoutId }


        if (indexOfWorkout != -1) {
            state.workouts[indexOfWorkout]=state.workouts[indexOfWorkout].addSet()
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