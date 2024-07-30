package com.example.liftlog.routine_feature.domain.repository

import com.example.liftlog.core.data.model.Routine
import io.realm.kotlin.query.RealmResults

interface RoutineDetailRepository {

    suspend fun getRoutineInfo(routineId:String): RealmResults<Routine>

    suspend fun addExerciseToRoutine(routineId: String,exerciseId:String)
}