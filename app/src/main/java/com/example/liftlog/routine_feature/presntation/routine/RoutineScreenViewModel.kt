package com.example.liftlog.routine_feature.presntation.routine

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.liftlog.routine_feature.presntation.routine.state.RoutineScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoutineScreenViewModel @Inject constructor():ViewModel() {


   lateinit var state:MutableState<RoutineScreenState>

   init {

   }
}