package com.example.liftlog.core.domain.repository

import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.data.repository.ExerciseRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmSingleQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(private val realm: Realm): ExerciseRepository {

    override suspend fun addExercise(exercise: Exercise) {

        realm.writeBlocking {

            copyToRealm(exercise)
        }
    }

    override suspend fun getAllExercises(): Flow<ResultsChange<Exercise>>{

        return realm.query<Exercise>().asFlow()



    }

    override suspend fun getExercise(exerciseID: String):RealmSingleQuery<Exercise> {

        return realm.query<Exercise>("_id == $exerciseID").first()
    }
}