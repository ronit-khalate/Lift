package com.example.liftlog.routine_feature.presntation.routine.event

import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.domain.dto.ExerciseDto

sealed interface RoutineScreenEvent {


    data class OnExerciseAdded(val map:List<Exercise>):RoutineScreenEvent


    data class OnRoutineNameEntered(val name:String):RoutineScreenEvent
    data class OnRoutineNoteEntered(val note:String):RoutineScreenEvent
    data object OnRoutineStartWorkOutClicked:RoutineScreenEvent
    data class OnDoneBtnClicked(val onCompleteUpserting:()->Unit):RoutineScreenEvent
}