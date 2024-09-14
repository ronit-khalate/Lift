package com.example.liftlog.routine_feature.presntation.routine.event

import com.example.liftlog.core.data.model.Exercise

sealed interface ExerciseListUiEvent {

    data class OnSearchQueryEntered(val query:String):ExerciseListUiEvent
    data class OnMuscleGroupSelect(val groupId:String):ExerciseListUiEvent
    data class OnExerciseSelect(val exercise:Exercise):ExerciseListUiEvent
}