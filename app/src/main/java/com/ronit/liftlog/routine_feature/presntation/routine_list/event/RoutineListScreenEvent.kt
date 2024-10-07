package com.ronit.liftlog.routine_feature.presntation.routine_list.event

import com.ronit.liftlog.core.data.model.Routine
import com.ronit.liftlog.routine_feature.presntation.routine.event.RoutineScreenEvent

sealed interface RoutineListScreenEvent {

    data object OnAddRoutineButtonClicked: RoutineListScreenEvent

    data class OnRoutineClicked(val navigate:()->Unit): RoutineListScreenEvent

    data class OnDeleteRoutine(val routine: Routine):RoutineListScreenEvent

}