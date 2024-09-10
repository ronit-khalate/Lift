package com.example.liftlog.start_routine_feature.data.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.data.model.Set
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import org.mongodb.kbson.ObjectId

data class ExerciseLogDto(

    val id :ObjectId = ObjectId(),
    val exerciseID: String,
    val name:String,
    val note:String,
    val muscleGroup:String,
    val setList: SnapshotStateList<SetDto> = mutableStateListOf()
)
