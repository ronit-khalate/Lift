package com.ronit.liftlog.core.presentation.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronit.liftlog.core.data.MuscleGroup
import com.ronit.liftlog.core.data.mappers.toExercise
import com.ronit.liftlog.core.data.model.entity.Exercise
import com.ronit.liftlog.core.domain.repository.ExerciseRepositoryImpl
import com.ronit.liftlog.core.domain.dto.ExerciseDto
import com.ronit.liftlog.core.domain.titlecase
import com.ronit.liftlog.core.presentation.exercise.event.ExerciseScreenEvent

import com.ronit.liftlog.core.presentation.component.DialogContent
import com.ronit.liftlog.core.presentation.exercise.state.ExerciseScreenState
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



    var muscleGroupIdx by mutableIntStateOf(-1)



    init {

        exerciseId?.let { id->

            viewModelScope.launch {
                repositoryImpl.getExercise(id)?.let {

                    exercise= ExerciseScreenState(
                        id =  it._id.toHexString(),
                        name = it.name,
                        note = it.note?:"",
                        setCount = it.setCount,


                    )

                    muscleGroupIdx = MuscleGroup.entries.find { mg->mg.name.lowercase()== it.primaryMuscles.first().lowercase() }?.ordinal?:-1
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
                muscleGroupIdx = event.idx

            }
            is ExerciseScreenEvent.OnNameChange -> {
                exercise = exercise.copy(name = event.name.replaceFirstChar { char -> char.titlecase() })
            }

            is ExerciseScreenEvent.OnDoneBtnClicked -> {



                    if(muscleGroupIdx == -1 && exercise.name.isBlank()){

                        exercise = exercise.copy(
                            showDialog = true,
                            dialogContent = DialogContent(
                                title = "Enter Exercise Info",
                                text = "Enter Exercise Information Before saving it",
                                confirmBtnText = "OK",
                                onConfirm = {
                                    exercise = exercise.copy(showDialog = false, dialogContent = null)
                                }
                            ),

                        )
                    }
                    else if(exercise.name.isBlank()){
                        exercise = exercise.copy(
                            showDialog = true,
                            dialogContent = DialogContent(
                                title = "Name is Empty",
                                text = "Please Enter Exercise Name Before Saving!",
                                confirmBtnText = "OK",
                                onConfirm = {
                                    exercise = exercise.copy(showDialog = false, dialogContent = null)
                                }
                            ),

                            )
                    }
                    else if(muscleGroupIdx == -1){

                        exercise = exercise.copy(
                            showDialog = true,
                            dialogContent = DialogContent(
                                title = "Choose Muscle Group",
                                text = "Exercise  must have muscle group please choose muscle group for the exercise before saving exercise!",
                                confirmBtnText = "OK",
                                onConfirm = {
                                    exercise = exercise.copy(showDialog = false, dialogContent = null)
                                }
                            ),

                            )
                    }
                    else {


                        val ex = ExerciseDto(
                            _id = exerciseId,
                            name = exercise.name,
                            muscleGroup = MuscleGroup.entries[muscleGroupIdx].name.titlecase(),
                            Note = exercise.note

                        )


                        onDoneBtnClicked(
                            exercise = ex.toExercise(),
                            onComplete = { event.onComplete() }
                        )
                    }



            }

            is ExerciseScreenEvent.OnDialogConfirmBtnClicked -> {
                exercise.dialogContent?.onConfirm?.invoke()
            }
            is ExerciseScreenEvent.OnDialogDismissBtnClicked -> {
                exercise.dialogContent?.onDismiss?.invoke()
            }
        }
    }


    fun isMuscleGroupSelected(idx: Int):Boolean{
        return  muscleGroupIdx==MuscleGroup.entries[idx].ordinal
    }

    private fun onDoneBtnClicked(exercise: Exercise, onComplete:()->Unit){




        if(exerciseId != null){

            viewModelScope.launch {

                repositoryImpl.upsertExercise(exercise)
                onComplete()
            }
        }
        else{


            viewModelScope.launch {


                repositoryImpl.upsertExercise(exercise)


            }.invokeOnCompletion {
                onComplete()
            }
        }
    }

    @AssistedFactory
    interface ExerciseViewModelFactory{

        fun create(exerciseId:String?):ExerciseViewModel
    }

}