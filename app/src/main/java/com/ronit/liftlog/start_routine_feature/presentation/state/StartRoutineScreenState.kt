package com.ronit.liftlog.start_routine_feature.presentation.state

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.ronit.liftlog.core.data.model.entity.ExerciseLog
import com.ronit.liftlog.core.data.model.entity.Routine
import com.ronit.liftlog.core.data.model.entity.Workout
import com.ronit.liftlog.start_routine_feature.data.model.ExerciseLogDto
import io.realm.kotlin.types.RealmInstant

data class StartRoutineScreenState(

    val routine : Routine? =null,
    val startTime: RealmInstant = RealmInstant.now(),
    val endTime: RealmInstant? = null,
    val date:Long=0L,
    val bodyWeight:String="",
    val workouts: SnapshotStateList<Workout> = mutableStateListOf(),

    val lastLog:List<ExerciseLog>? =null
)