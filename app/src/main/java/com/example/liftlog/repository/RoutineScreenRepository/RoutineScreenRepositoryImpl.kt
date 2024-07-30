package com.example.liftlog.repository.RoutineScreenRepository

import com.example.liftlog.data.Exercise
import com.example.liftlog.data.Routine
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import javax.inject.Inject

class RoutineScreenRepositoryImpl @Inject  constructor(
    private val realm: Realm
):RoutineScreenRepository {
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