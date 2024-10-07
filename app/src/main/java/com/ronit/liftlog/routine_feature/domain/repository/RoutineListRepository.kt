package com.ronit.liftlog.routine_feature.domain.repository

import com.ronit.liftlog.core.data.model.Routine
import kotlinx.coroutines.flow.Flow

interface RoutineListRepository {

    suspend fun getAllRoutine(): Flow<List<Routine>>
    suspend fun addRoutine(routine: Routine)

    suspend fun removeRoutine(routine: Routine)

}