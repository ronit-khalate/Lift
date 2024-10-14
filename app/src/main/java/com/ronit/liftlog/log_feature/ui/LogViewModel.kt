package com.ronit.liftlog.log_feature.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronit.liftlog.core.domain.Until
import com.ronit.liftlog.core.domain.toLocalDate
import com.ronit.liftlog.log_feature.data.repository.LogRepository
import com.ronit.liftlog.log_feature.domain.LogRepositoryImpl
import com.ronit.liftlog.log_feature.ui.event.LogScreenUiEvent
import com.ronit.liftlog.log_feature.ui.state.LogScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class LogViewModel@Inject constructor(
    private val logRepo:LogRepositoryImpl
):ViewModel() {




    var state  by mutableStateOf(LogScreenUiState())

    init {

        viewModelScope.launch {

            val startDate = logRepo.getFirstLog()?.date?.toLocalDate()

            val logs = logRepo.getLog(state.currentDate).sortedByDescending { it.startTime.epochSeconds }

            startDate?.let {
                state=state.copy(
                    dateList = startDate Until  LocalDate.now(),
                    logs = logs
                )
            }

        }
    }



    fun onEvent(event:LogScreenUiEvent){

        when(event){
            is LogScreenUiEvent.OnDateClicked -> {


                viewModelScope.launch {

                    changeDate(event.date)

                }


            }
            is LogScreenUiEvent.OnRoutineLogCardClicked -> TODO()
            is LogScreenUiEvent.OnGoToTodaysLog -> {


                viewModelScope.launch {

                    changeDate(state.currentDate)
                }

            }
        }
    }

    private suspend fun changeDate(date: LocalDate){
        val logs = logRepo.getLog(date)
        state = state.copy(
            selectedDate = date,
            logs = logs
        )
    }






}