package com.example.liftlog.routine_feature.presntation.routine.state

import com.example.liftlog.core.data.model.Exercise

data class RoutineScreenState(
    val routineId:String,
    val routineName:String,
    val note:String,
    val exerciseList:List<Exercise>
)