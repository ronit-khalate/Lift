package com.ronit.liftlog.routine_feature.domain.model

import com.ronit.liftlog.core.domain.dto.ExerciseDto

data class RoutineDto(
    var _id:String="",
    var name:String="",
    var note:String="",
    var exercise: List<ExerciseDto> = emptyList(),
    var date: String="",
)

