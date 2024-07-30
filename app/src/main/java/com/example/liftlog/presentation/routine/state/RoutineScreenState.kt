package com.example.liftlog.presentation.routine.state

import com.example.liftlog.data.Exercise

data class RoutineScreenState(
    val routineId:String,
    val routineName:String,
    val note:String,
    val exerciseList:List<Exercise>
)