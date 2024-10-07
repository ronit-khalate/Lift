package com.ronit.liftlog.routine_feature.presntation.routine.state

import com.ronit.liftlog.core.data.model.Exercise
import com.ronit.liftlog.core.presentation.component.DialogContent
import org.mongodb.kbson.ObjectId

data class RoutineScreenState(
    val routineId:ObjectId? =null,
    val routineName:String="",
    val note:String="",
    val exerciseList:List<Exercise> = emptyList(),
    val dialogContent: DialogContent?=null
)