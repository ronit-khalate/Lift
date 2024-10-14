package com.ronit.liftlog.log_feature.ui.event

import java.time.LocalDate

sealed interface LogScreenUiEvent {

    data class OnDateClicked(val date:LocalDate):LogScreenUiEvent
    data class OnRoutineLogCardClicked(val routineId:String):LogScreenUiEvent
    data object OnGoToTodaysLog:LogScreenUiEvent

}