package com.ronit.liftlog.log_feature.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronit.liftlog.core.domain.Until
import com.ronit.liftlog.core.domain.toEpochMillis
import com.ronit.liftlog.core.domain.toLocalDate
import com.ronit.liftlog.log_feature.domain.LogRepositoryImpl
import com.ronit.liftlog.log_feature.ui.event.LogScreenUiEvent
import com.ronit.liftlog.log_feature.ui.state.LogScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class LogViewModel@Inject constructor(
    private val logRepo:LogRepositoryImpl
):ViewModel() {




    var state  by mutableStateOf(LogScreenUiState())

    init {

        viewModelScope.launch {

            val startDate = logRepo.getFirstLog()?.date?.toLocalDate()

            startDate?.let {
                state=state.copy(
                    dateList = startDate Until  LocalDate.now(),
                )
            }

            logRepo.getAllLog().collect{
                state=state.copy(
                    allLogs = it,
                    specificDateLog = it.filter {  it.date == state.selectedDate.toEpochMillis() }.sortedByDescending { it.startTime.epochSeconds }
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

            is LogScreenUiEvent.OnDeleteLog -> {



                    viewModelScope.launch {

                        logRepo.removeLog(event.log)
                    }


            }
        }
    }

    private fun changeDate(date: LocalDate){
        val logs = state.allLogs.filter { it.date == date.toEpochMillis() }.sortedByDescending { it.startTime.epochSeconds }
        state = state.copy(
            selectedDate = date,
            specificDateLog = logs
        )
    }






}