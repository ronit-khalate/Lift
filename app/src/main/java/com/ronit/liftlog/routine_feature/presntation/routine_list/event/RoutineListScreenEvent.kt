package com.ronit.liftlog.routine_feature.presntation.routine_list.event

import com.ronit.liftlog.core.data.model.entity.Routine

sealed interface RoutineListScreenEvent {

    data object OnAddRoutineButtonClicked: RoutineListScreenEvent

    data class OnRoutineClicked(val navigate:()->Unit): RoutineListScreenEvent

    data class OnDeleteRoutine(val routine: Routine):RoutineListScreenEvent

}