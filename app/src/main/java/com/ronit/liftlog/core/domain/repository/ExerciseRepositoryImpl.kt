package com.ronit.liftlog.core.domain.repository

import com.ronit.liftlog.core.data.model.Exercise
import com.ronit.liftlog.core.data.model.Routine
import com.ronit.liftlog.core.data.repository.ExerciseRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(private val realm: Realm): ExerciseRepository {



    override suspend fun getAllExercises(): Flow<List<Exercise>> {

        return realm.query<Exercise>().asFlow()
            .map { result->

                result.list.toList()
            }



    }

    override suspend fun getExercise(exerciseID: String): Exercise? {

        return realm.query<Exercise>("_id == $0",ObjectId(exerciseID)).first().find()
    }

    override suspend fun upsertExercise(exercise: Exercise) {

        realm.writeBlocking {

            val managedExercise = query<Exercise>("_id==$0", exercise._id).find().firstOrNull()


            if(managedExercise != null){
                managedExercise.name=exercise.name
                managedExercise.note=exercise.note
                managedExercise.muscleGroup=exercise.muscleGroup

                copyToRealm(managedExercise,UpdatePolicy.ALL)
            }
            else{
                copyToRealm(exercise)
            }





        }
    }

    override suspend fun getAllRoutinesExercises(routineId: String): Array<Exercise> {

        return realm.query<Routine>("_id == $0", ObjectId(routineId)).first().find()?.exercise?.toTypedArray() ?: emptyArray()
    }
}