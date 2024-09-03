package com.example.liftlog.core.domain.repository

import com.example.liftlog.core.data.mappers.toExerciseDto
import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.core.data.repository.ExerciseRepository
import com.example.liftlog.core.domain.dto.ExerciseDto
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmSingleQuery
import io.realm.kotlin.query.find
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(private val realm: Realm): ExerciseRepository {

    override suspend fun addExercise(exercise: Exercise) {

        realm.writeBlocking {

            copyToRealm(exercise)
        }
    }

    override suspend fun getAllExercises(): Flow<List<Exercise>> {

        return realm.query<Exercise>().asFlow()
            .map { result->

                result.list.toList()
            }



    }

    override suspend fun getExercise(exerciseID: String): Exercise? {

        return realm.query<Exercise>("_id == $0",ObjectId(exerciseID)).first().find()
    }

    override suspend fun getAllRoutinesExercises(routineId: String): Array<Exercise> {

        return realm.query<Routine>("_id == $0", ObjectId(routineId)).first().find()?.exercise?.toTypedArray() ?: emptyArray()
    }
}