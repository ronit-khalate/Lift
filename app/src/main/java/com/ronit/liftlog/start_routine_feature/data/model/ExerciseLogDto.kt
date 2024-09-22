package com.ronit.liftlog.start_routine_feature.data.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import org.mongodb.kbson.ObjectId

data class ExerciseLogDto(

    val id :ObjectId = ObjectId(),
    val exerciseID: String,
    val name:String,
    val note:String,
    val muscleGroup:String,
    val setList: SnapshotStateList<SetDto> = mutableStateListOf()
)
