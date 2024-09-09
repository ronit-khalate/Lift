package com.example.liftlog.start_routine_feature.domain.repository

import com.example.liftlog.core.data.model.Log
import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.core.domain.RealmResponse
import com.example.liftlog.start_routine_feature.presentation.state.StartRoutineScreenState
import org.mongodb.kbson.ObjectId

interface StartRoutineRepository {

    suspend fun getRoutine(id:ObjectId):RealmResponse<Routine>
    suspend fun getLastLogOfRoutineOrNull(routineId:ObjectId):RealmResponse<Log?>
    suspend fun saveLog(state:StartRoutineScreenState):RealmResponse<Unit>
}