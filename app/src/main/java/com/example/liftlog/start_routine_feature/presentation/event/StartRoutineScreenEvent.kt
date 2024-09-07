package com.example.liftlog.start_routine_feature.presentation.event

sealed interface StartRoutineScreenEvent {

    data class OnAddSetInExerciseLog(val id:String):StartRoutineScreenEvent

    data class OnUpdateWeight(val id: String, val data: String, val exLogId: String):StartRoutineScreenEvent
    data class OnUpdateReps(val id: String, val data: String, val exLogId: String) :StartRoutineScreenEvent
    data class OnUpdateNotes(val id: String, val data: String, val exLogId: String) : StartRoutineScreenEvent
    data object OnRoutineFinish:StartRoutineScreenEvent

}