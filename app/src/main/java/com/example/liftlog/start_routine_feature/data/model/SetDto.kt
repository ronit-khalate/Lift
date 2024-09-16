package com.example.liftlog.start_routine_feature.data.model

import com.example.liftlog.core.data.model.Exercise
import org.mongodb.kbson.ObjectId

data class SetDto(
    val id:ObjectId = ObjectId(),
    val setNo:Int =0,
    val exerciseId: String,
    val weight:String="",
    val repetitions:String="",
    val notes:String="" ,
    var prevWeight:String="0",
    var prevRepetitions:String="0",
    var prevNotes:String=""
)
