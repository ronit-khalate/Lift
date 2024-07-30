package com.example.liftlog.presentation.routine.event

sealed class RoutinesScreenEvent {

    data object OnAddRoutineButtonClicked:RoutinesScreenEvent()

    data class OnRoutineClicked(val navigate:()->Unit):RoutinesScreenEvent()

}