package com.ronit.liftlog.log_feature.ui.state

import com.ronit.liftlog.core.data.model.entity.Log
import java.time.LocalDate

data class LogScreenUiState(
    val dateList:List<LocalDate> = emptyList(),
    val specificDateLog:List<Log> = emptyList(),
    val allLogs:List<Log> = emptyList(),
    val selectedDate:LocalDate=LocalDate.now(),
){

    val currentDate = LocalDate.now()
}
