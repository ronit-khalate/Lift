package com.ronit.liftlog.core.presentation.exercise.state

import com.ronit.liftlog.core.presentation.component.DialogContent

data class ExerciseScreenState(
    val  id:String="",
    val name:String="",
    val note:String="",
    var muscleGroupIdx:Int=-1,
    var setCount:Int =1,
    val showDialog:Boolean = false,
    val dialogContent: DialogContent?=null


)






