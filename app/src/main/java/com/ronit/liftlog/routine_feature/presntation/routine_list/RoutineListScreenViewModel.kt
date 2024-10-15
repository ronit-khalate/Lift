package com.ronit.liftlog.routine_feature.presntation.routine_list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronit.liftlog.core.data.model.entity.Routine
import com.ronit.liftlog.routine_feature.data.RoutineListRepositoryImpl
import com.ronit.liftlog.routine_feature.presntation.routine_list.event.RoutineListScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineListScreenViewModel @Inject constructor(
    private val repository:RoutineListRepositoryImpl
):ViewModel() {


    var routineList = mutableStateListOf<Routine>()

    init {
        viewModelScope.launch {
            repository.getAllRoutine().collect{

                routineList.clear()
                routineList.addAll(it)
            }
        }

    }
    fun onEvent(event: RoutineListScreenEvent){

        when(event){
            is RoutineListScreenEvent.OnAddRoutineButtonClicked -> {

            }
            is RoutineListScreenEvent.OnRoutineClicked ->{
                event.navigate()
            }

            is RoutineListScreenEvent.OnDeleteRoutine -> {


                viewModelScope.launch {

                    repository.removeRoutine(event.routine)
                }

            }
        }
    }
}