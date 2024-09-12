package com.example.liftlog.routine_feature.presntation.routine_list.event

sealed interface RoutineListScreenEvent {

    data object OnAddRoutineButtonClicked: RoutineListScreenEvent

    data class OnRoutineClicked(val navigate:()->Unit): RoutineListScreenEvent

}