package com.ronit.liftlog.start_routine_feature.data.repository

//import com.example.liftlog.core.data.mappers.toLog
import androidx.core.util.Predicate
import com.ronit.liftlog.core.data.mappers.toRealmList
import com.ronit.liftlog.core.data.model.entity.Exercise
import com.ronit.liftlog.core.data.model.entity.ExerciseLog
import com.ronit.liftlog.core.data.model.entity.Log
import com.ronit.liftlog.core.data.model.entity.Routine
import com.ronit.liftlog.core.data.model.entity.Set
import com.ronit.liftlog.core.domain.RealmResponse
import com.ronit.liftlog.core.domain.toEpochMillis
import com.ronit.liftlog.start_routine_feature.domain.repository.StartRoutineRepository
import com.ronit.liftlog.start_routine_feature.presentation.state.StartRoutineScreenState
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmInstant
import org.mongodb.kbson.ObjectId
import java.time.LocalDate
import javax.inject.Inject


class StartRoutineRepositoryImpl @Inject constructor(
    private val realm:Realm,
): StartRoutineRepository {

    override suspend fun getRoutine(id: ObjectId): RealmResponse<Routine> {

       return try {

           val t = realm.query<Routine>().find()
            val routine = realm.query<Routine>("_id == $0", id).find().first()

           RealmResponse.Success(routine)


       }
       catch (e:Exception){

           RealmResponse.Error(e)
       }
    }

    override suspend fun getLastLogOfRoutineOrNull(routineId: ObjectId): RealmResponse<Log?> {

        return try {



            val t = realm.query<Log>()
            val result = realm.query<Log>("routineId == $0" , routineId).find().sortedByDescending {  it.date}

            val log = result.last()

            val _result = realm.copyFromRealm(obj = log)

            RealmResponse.Success(data = _result)

        }
        catch (e:Exception){

            RealmResponse.Error(error = e)
        }
    }

    override suspend fun saveLog(state: StartRoutineScreenState): RealmResponse<Unit> {

        return try {


            for(workoutIdx in state.workouts.indices){


                state.workouts[workoutIdx].sets = state.workouts[workoutIdx].sets.filter { it.isEmpty() }.toRealmList()

            }

            /**
             * if no sets in workout hence we won't save the workout
             * */
            val filteredWorkouts= state.workouts.filter { it.sets.isNotEmpty() }

            realm.writeBlocking {

                val log = Log().apply {

                    this.routine = state.routine?.let { findLatest(state.routine)}
                    this.workouts =filteredWorkouts.toRealmList()
                    this.endTime = RealmInstant.now()
                    this.date = LocalDate.now().toEpochMillis()


                }





                copyToRealm(log)
            }
            RealmResponse.Success(Unit)
        }
        catch (e:Exception){

            RealmResponse.Error(e)
        }
    }
}


inline fun <T, R> Iterable<T>.mapIf(predicate: (T)->Boolean,transform: (T) -> R): List<R> {

    val list = mutableListOf<R>()

    for( element in this){

        if(predicate(element)){

            list.add(transform(element))
        }
    }

    return list.toList()
}
