package com.ronit.liftlog.routine_feature.presntation.routine.event

import com.ronit.liftlog.core.data.MuscleGroup
import com.ronit.liftlog.core.data.model.entity.Exercise

sealed interface ExerciseListUiEvent {

    data class OnSearchQueryEntered(val query:String):ExerciseListUiEvent

    data class OnExerciseSelect(val exercise: Exercise):ExerciseListUiEvent

    data class MuscleGroupSelected(val group:MuscleGroup?):ExerciseListUiEvent
}