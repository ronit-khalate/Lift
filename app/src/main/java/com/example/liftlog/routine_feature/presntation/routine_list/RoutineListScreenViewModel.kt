package com.example.liftlog.routine_feature.presntation.routine_list

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.routine_feature.data.RoutineListRepositoryImpl
import com.example.liftlog.routine_feature.domain.repository.RoutineListRepository
import com.example.liftlog.routine_feature.presntation.routine_list.event.RoutineListScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RoutineListScreenViewModel @Inject constructor(
    private val repository:RoutineListRepositoryImpl
):ViewModel() {


    var routineList = mutableStateListOf<Routine>()

    init {
        viewModelScope.launch {
            repository.getAllRoutine().collect{
                routineList.addAll(it.list)
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
        }
    }
}