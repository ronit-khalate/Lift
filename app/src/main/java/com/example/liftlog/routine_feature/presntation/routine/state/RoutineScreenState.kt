package com.example.liftlog.routine_feature.presntation.routine.state

import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.domain.dto.ExerciseDto
import com.example.liftlog.core.navigation.Screen.Screens
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import org.mongodb.kbson.ObjectId

data class RoutineScreenState(
    val routineId:ObjectId? =null,
    val routineName:String="",
    val note:String="",
    val exerciseList:RealmList<Exercise> = realmListOf()
)