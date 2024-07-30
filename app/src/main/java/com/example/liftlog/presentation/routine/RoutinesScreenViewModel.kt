package com.example.liftlog.presentation.routine

import androidx.lifecycle.ViewModel
import com.example.liftlog.presentation.routine.event.RoutinesScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class RoutinesScreenViewModel():ViewModel() {




    fun onEvent(event: RoutinesScreenEvent){

        when(event){
            is RoutinesScreenEvent.OnAddRoutineButtonClicked -> {

            }
            is RoutinesScreenEvent.OnRoutineClicked ->{
                event.navigate()
            }
        }
    }
}