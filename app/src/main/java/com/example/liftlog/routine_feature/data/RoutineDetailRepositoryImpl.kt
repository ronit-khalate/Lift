package com.example.liftlog.routine_feature.data

import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.routine_feature.domain.repository.RoutineDetailRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import javax.inject.Inject

class RoutineDetailRepositoryImpl @Inject  constructor(
    private val realm: Realm
): RoutineDetailRepository {
    override suspend fun getRoutineInfo(routineId: String):RealmResults<Routine> {
        return realm.query<Routine>("routineID == ${routineId}").find()
    }

    override suspend fun addExerciseToRoutine(routineId: String,exerciseID:String) {
        realm.writeBlocking {

            val routine = query<Routine>("_id == $routineId").find().first()
            val exercise = query<Exercise>("_id == $exerciseID").find().first()

            routine.exercise.add(exercise)
        }
    }
}