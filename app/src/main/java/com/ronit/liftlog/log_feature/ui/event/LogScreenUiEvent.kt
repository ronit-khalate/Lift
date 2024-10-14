package com.ronit.liftlog.log_feature.ui.event

import com.ronit.liftlog.core.data.model.entity.Log
import java.time.LocalDate

sealed interface LogScreenUiEvent {

    data class OnDateClicked(val date:LocalDate):LogScreenUiEvent
    data class OnRoutineLogCardClicked(val routineId:String):LogScreenUiEvent
    data object OnGoToTodaysLog:LogScreenUiEvent

    data class OnDeleteLog(val log: Log):LogScreenUiEvent

}