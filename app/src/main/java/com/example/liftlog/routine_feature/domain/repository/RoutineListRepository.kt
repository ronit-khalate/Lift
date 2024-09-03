package com.example.liftlog.routine_feature.domain.repository

import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.routine_feature.domain.model.RoutineDto
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.SingleQueryChange
import kotlinx.coroutines.flow.Flow

interface RoutineListRepository {

    suspend fun getAllRoutine(): Flow<List<Routine>>
    suspend fun addRoutine(routine: Routine)

}