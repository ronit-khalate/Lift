package com.example.liftlog.core.presentation.exercise.state

import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.data.model.Set
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList

data class ExerciseScreenState(
    val  id:String="",
    val name:String="",
    val note:String="",
    var muscleGroup:String="",
    var setCount:Int =1,

)





