package com.example.liftlog.core.presentation.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftlog.core.data.mappers.toExercise
import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.domain.repository.ExerciseRepositoryImpl
import com.example.liftlog.core.domain.dto.ExerciseDto
import com.example.liftlog.core.presentation.exercise.event.ExerciseScreenEvent
import com.example.liftlog.core.presentation.exercise.state.ExerciseScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


@HiltViewModel(assistedFactory = ExerciseViewModel.ExerciseViewModelFactory::class)
class ExerciseViewModel @AssistedInject constructor(
    @Assisted private val exerciseId:String?=null,
    private val repositoryImpl: ExerciseRepositoryImpl
):ViewModel() {


    var exercise by mutableStateOf(ExerciseScreenState())
        private set


    init {

        exerciseId?.let { id->

            viewModelScope.launch {
                repositoryImpl.getExercise(id)?.let {

                    exercise= ExerciseScreenState(
                        id =  it._id.toHexString(),
                        name = it.name,
                        note = it.note?:"",
                        muscleGroup = it.muscleGroup?:"",
                        setCount = it.setCount,
                        sets = it.sets

                    )
                }
            }

        }
    }

    fun onEvent(event: ExerciseScreenEvent){

        when(event){
            is ExerciseScreenEvent.OnNoteChange -> {

                exercise = exercise.copy(note = event.note)
            }
            is ExerciseScreenEvent.OnMuscleGroupChange -> {
                exercise = exercise.copy(muscleGroup = event.muscleGroup)
            }
            is ExerciseScreenEvent.OnNameChange -> {
                exercise = exercise.copy(name = event.name)
            }

            is ExerciseScreenEvent.onDoneBtnClicked -> {

                viewModelScope.launch {
                    val ex = ExerciseDto(
                        name = exercise.name,
                        muscleGroup = exercise.muscleGroup,
                        Note = exercise.note

                    )

                    repositoryImpl.addExercise(ex.toExercise())

                    event.onComplete()
                }
            }
        }
    }

    @AssistedFactory
    interface ExerciseViewModelFactory{

        fun create(exerciseId:String?):ExerciseViewModel
    }

}