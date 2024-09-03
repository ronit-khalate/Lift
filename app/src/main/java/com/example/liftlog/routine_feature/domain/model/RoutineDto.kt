package com.example.liftlog.routine_feature.domain.model

import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.domain.dto.ExerciseDto
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import org.mongodb.kbson.BsonObjectId

data class RoutineDto(
    var _id:String="",
    var name:String="",
    var note:String="",
    var exercise: List<ExerciseDto> = emptyList(),
    var date: String="",
)

