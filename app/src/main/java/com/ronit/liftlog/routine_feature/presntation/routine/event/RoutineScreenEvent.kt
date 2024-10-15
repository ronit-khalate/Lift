package com.ronit.liftlog.routine_feature.presntation.routine.event

import com.ronit.liftlog.core.data.model.entity.Exercise

sealed interface RoutineScreenEvent {


    data class OnExerciseAdded(val map:List<Exercise>):RoutineScreenEvent


    data class OnRoutineNameEntered(val name:String):RoutineScreenEvent
    data class OnRoutineNoteEntered(val note:String):RoutineScreenEvent
    data object OnRoutineStartWorkOutClicked:RoutineScreenEvent
    data class OnDoneBtnClicked(val onCompleteUpserting:()->Unit):RoutineScreenEvent

    data class OnRemoveExercise(val id:String ):RoutineScreenEvent

}