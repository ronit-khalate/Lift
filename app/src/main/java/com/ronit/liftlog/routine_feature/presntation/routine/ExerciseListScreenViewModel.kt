package com.ronit.liftlog.routine_feature.presntation.routine

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronit.liftlog.core.data.MuscleGroup
import com.ronit.liftlog.core.data.model.entity.Exercise
import com.ronit.liftlog.core.domain.repository.ExerciseRepositoryImpl
import com.ronit.liftlog.core.domain.rabinKarpSimilarity
import com.ronit.liftlog.routine_feature.presntation.routine.event.ExerciseListUiEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ExerciseListScreenViewModel.ExerciseLostScreenViewModelFactory::class)
class ExerciseListScreenViewModel @AssistedInject constructor(
    @Assisted private val routineId:String?,
    private val exerciseListScreenRepository: ExerciseRepositoryImpl
):ViewModel() {


    var searchQuery by mutableStateOf("")
        private set


    private var exerciseListFlow :MutableStateFlow<List<Exercise>> = MutableStateFlow(emptyList())
        private set


    var shownExercise  :SnapshotStateList<Exercise> = mutableStateListOf()
        private set

    var selectedExercises: SnapshotStateList<Exercise> = mutableStateListOf()
        private set

    var selectedMuscleGroup by mutableStateOf<MuscleGroup?>(null)

    init {

        viewModelScope.launch {

            launch {


                exerciseListScreenRepository.getAllExercises().collect { result ->

                    exerciseListFlow.value = result
                    shownExercise.addAll(exerciseListFlow.value)
                }
            }

            routineId?.let {


                launch {

                    selectedExercises = mutableStateListOf(
                        *exerciseListScreenRepository.getAllRoutinesExercises(routineId)
                    )
                }
            }
        }
    }


    fun onExerciseSelected(exercise: Exercise){

        if(selectedExercises.any { it._id.toHexString()== exercise._id.toHexString() }){

            selectedExercises.removeIf {
                it._id.toHexString()==exercise._id.toHexString()
            }
        }
        else{

            selectedExercises.add(exercise)
        }
    }


    fun onUiEvent(event:ExerciseListUiEvent){

        when(event){
            is ExerciseListUiEvent.OnExerciseSelect -> {
                onExerciseSelected(event.exercise)
            }
            is ExerciseListUiEvent.OnSearchQueryEntered -> {
                searchQuery = event.query

            }

            is ExerciseListUiEvent.MuscleGroupSelected -> {

                selectedMuscleGroup=event.group


              val list =  if(event.group != null){

                    val muscleGroupName = event.group.name.replace("_"," ").lowercase()

                    exerciseListFlow.value.filter {
    //                        Log.d("filter" ,"${it.primaryMuscles.first().lowercase()} ${event.group.name.replace("_"," ").lowercase()}")
                        it.primaryMuscles.firstOrNull()?.lowercase() == muscleGroupName
                    }
                } else{
                    exerciseListFlow.value
                }

                shownExercise.clear()
                shownExercise.addAll(list)


            }


        }
    }

    fun onSearchQueryEntered(query:String){



       val lis = if(query.isBlank()) {


           shownExercise.sortedBy{ it.name }
       }
       else{




            shownExercise.sortedByDescending {
                    rabinKarpSimilarity(it.name, query)
            }

       }

        shownExercise.clear()

        shownExercise.addAll(lis)
    }


    @AssistedFactory
    interface ExerciseLostScreenViewModelFactory{

        fun create(routineId:String?=null):ExerciseListScreenViewModel
    }

}