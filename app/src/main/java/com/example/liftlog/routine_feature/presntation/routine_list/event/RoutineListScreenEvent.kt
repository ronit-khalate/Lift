package com.example.liftlog.routine_feature.presntation.routine_list.event

sealed class RoutineListScreenEvent {

    data object OnAddRoutineButtonClicked: RoutineListScreenEvent()

    data class OnRoutineClicked(val navigate:()->Unit): RoutineListScreenEvent()

}