package com.ronit.liftlog.start_routine_feature.presentation.event

import com.ronit.liftlog.core.data.model.entity.Workout
import org.mongodb.kbson.ObjectId

sealed interface StartRoutineScreenEvent {

    data class OnAddSetInExerciseLog(val id:String):StartRoutineScreenEvent

    data class OnUpdateWeight(val setId: ObjectId,val workoutId: ObjectId ,val data: String):StartRoutineScreenEvent
    data class OnUpdateReps(val setId: ObjectId,val workoutId: ObjectId , val data: String) :StartRoutineScreenEvent
    data class OnUpdateNotes(val setId: ObjectId,val workoutId: ObjectId , val data: String) : StartRoutineScreenEvent
    data object OnRoutineFinish:StartRoutineScreenEvent
    data class OnBodyWeightEntered(val weight:String):StartRoutineScreenEvent

}