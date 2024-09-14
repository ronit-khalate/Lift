package com.example.liftlog.routine_feature.presntation.routine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.domain.repository.ExerciseRepositoryImpl
import com.example.liftlog.core.domain.dto.ExerciseDto
import com.example.liftlog.core.domain.rabinKarpSimilarity
import com.example.liftlog.routine_feature.presntation.routine.event.ExerciseListUiEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel(assistedFactory = ExerciseListScreenViewModel.ExerciseLostScreenViewModelFactory::class)
class ExerciseListScreenViewModel @AssistedInject constructor(
    @Assisted private val routineId:String?,
    private val exerciseListScreenRepository: ExerciseRepositoryImpl
):ViewModel() {


    var searchQuery by mutableStateOf("")
        private set


    var exerciseListFlow :MutableStateFlow<List<Exercise>> = MutableStateFlow(emptyList())
        private set
    var selectedExercises: SnapshotStateList<Exercise> = mutableStateListOf()
        private set

    init {

        viewModelScope.launch {

            launch {


                exerciseListScreenRepository.getAllExercises().collect { result ->

                    exerciseListFlow.value = result
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


    fun onExerciseSelected(exercise:Exercise){

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
            is ExerciseListUiEvent.OnMuscleGroupSelect -> {

            }
            is ExerciseListUiEvent.OnSearchQueryEntered -> {
                searchQuery = event.query
            }
        }
    }

    fun onSearchQueryEntered(query:String){


        if(query.isBlank()) {


            exerciseListFlow.value = exerciseListFlow.value.sortedByDescending {

                it.name
            }
        }
        else{
            exerciseListFlow.value = exerciseListFlow.value.sortedByDescending {

                rabinKarpSimilarity(it.name, query)
            }
        }
    }


    @AssistedFactory
    interface ExerciseLostScreenViewModelFactory{

        fun create(routineId:String?=null):ExerciseListScreenViewModel
    }

}