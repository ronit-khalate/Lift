package com.example.liftlog.core.presentation.exercise.dto

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList

data class ExerciseDto(

    val _id:String?=null,
    var name:String="",
    var Note:String?=null,
    var muscleGroup:String?=null
)
