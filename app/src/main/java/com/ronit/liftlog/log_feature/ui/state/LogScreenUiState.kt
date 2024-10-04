package com.ronit.liftlog.log_feature.ui.state

import com.ronit.liftlog.core.data.model.Log
import java.time.LocalDate

data class LogScreenUiState(
    val dateList:List<LocalDate> = emptyList(),
    val logs:List<Log> = emptyList(),
    val selectedDate:LocalDate?=null,
){

    val currentDate = LocalDate.now()
}
