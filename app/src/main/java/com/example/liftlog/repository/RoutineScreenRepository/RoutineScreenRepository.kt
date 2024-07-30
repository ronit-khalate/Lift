package com.example.liftlog.repository.RoutineScreenRepository

import com.example.liftlog.data.Routine
import io.realm.kotlin.query.RealmResults

interface RoutineScreenRepository {

    suspend fun getRoutineInfo(routineId:String): RealmResults<Routine>

    suspend fun addExerciseToRoutine(routineId: String,exerciseId:String)
}