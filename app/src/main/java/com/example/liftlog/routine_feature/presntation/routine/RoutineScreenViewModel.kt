package com.example.liftlog.routine_feature.presntation.routine

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.routine_feature.data.RoutineDetailRepositoryImpl
import com.example.liftlog.routine_feature.presntation.routine.state.RoutineScreenState
import com.example.liftlog.routine_feature.presntation.routine_list.RoutineListScreenViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel(assistedFactory = RoutineScreenViewModel.RoutineScreenViewModelFactory::class)
class RoutineScreenViewModel @AssistedInject constructor(
   @Assisted private val routineId:String?,
   private val routineDetailRepositoryImpl: RoutineDetailRepositoryImpl
):ViewModel() {


   @AssistedFactory
   interface RoutineScreenViewModelFactory{

      fun create(routineId:String?=null):RoutineScreenViewModel
   }


   var state  by mutableStateOf(RoutineScreenState())
      private set


   init {

      routineId?.let { id->

         viewModelScope.launch {

            val routine = routineDetailRepositoryImpl.getRoutineInfo(id).first()

            state= RoutineScreenState(routineId = id, routineName = routine.name, note = routine.note, exerciseList = routine.exercise.toList())
         }
      }?:{
         state =RoutineScreenState()
      }
   }
}