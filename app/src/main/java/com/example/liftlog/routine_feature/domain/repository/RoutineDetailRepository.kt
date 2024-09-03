package com.example.liftlog.routine_feature.domain.repository

import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.routine_feature.domain.model.RoutineDto
import com.example.liftlog.routine_feature.presntation.routine.state.RoutineScreenState
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.query.RealmResults

interface RoutineDetailRepository {

    suspend fun getRoutineInfo(routineId:String): Routine?

    suspend fun addExerciseToRoutine(routineId: String,exerciseId:String)

    suspend fun saveRoutine(routine: RoutineScreenState)

    suspend fun updateRoutine(routine: RoutineScreenState)

}