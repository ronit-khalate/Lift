package com.ronit.liftlog.core.domain.repository

import com.ronit.liftlog.core.data.model.entity.Exercise
import com.ronit.liftlog.core.data.model.entity.Routine
import com.ronit.liftlog.core.data.repository.ExerciseRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.internal.format
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

    override suspend fun getExercises(ids: Collection<ObjectId>): List<Exercise> {


        val exercises = mutableListOf<Exercise>()


        for (id in ids){

            val exercise = realm.query<Exercise>("_id==$0" , id).find().firstOrNull()?:continue

            exercises.add(exercise)
        }

        return  exercises
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

    override suspend fun upsertAllExercises(exercises: RealmList<Exercise>) {

        realm.write {

            exercises.forEach {

                copyToRealm(it,UpdatePolicy.ALL)
            }
        }
    }

    override suspend fun getAllExerciseCount(): Int {
        return  realm.query<Exercise>().find().count()
    }



    override suspend fun getAllRoutinesExercises(routineId: String): List<Exercise> {


        val exercises:MutableList<Exercise> = mutableListOf()
        val routine = realm.query<Routine>("_id == $0" , ObjectId(routineId)).find().firstOrNull()?: return  exercises


        for(id in routine.exerciseIds){
             val exercise = realm.query<Exercise>("_id == $0" , id).find().firstOrNull()

            exercise?.let {

                exercises.add(it)
            }
        }


        return exercises
    }
}