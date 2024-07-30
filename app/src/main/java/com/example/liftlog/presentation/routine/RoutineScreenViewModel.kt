package com.example.liftlog.presentation.routine

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.liftlog.presentation.routine.state.RoutineScreenState
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class RoutineScreenViewModel():ViewModel() {


   lateinit var state:MutableState<RoutineScreenState>

   init {

   }
}