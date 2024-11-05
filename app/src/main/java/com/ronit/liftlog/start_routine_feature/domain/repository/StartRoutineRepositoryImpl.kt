package com.ronit.liftlog.start_routine_feature.domain.repository

//import com.example.liftlog.core.data.mappers.toLog
import com.ronit.liftlog.core.data.mappers.toRealmList
import com.ronit.liftlog.core.data.model.entity.BodyWeight
import com.ronit.liftlog.core.data.model.entity.Log
import com.ronit.liftlog.core.data.model.entity.Routine
import com.ronit.liftlog.core.domain.RealmResponse
import com.ronit.liftlog.core.domain.toEpochMillis
import com.ronit.liftlog.start_routine_feature.data.repository.StartRoutineRepository
import com.ronit.liftlog.start_routine_feature.presentation.state.StartRoutineScreenState
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmInstant
import org.mongodb.kbson.ObjectId
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
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


            /**
             * remove empty sets
             * */
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
                    this.routineName = state.routine?.name?:""
                    this.workouts =filteredWorkouts.toRealmList()
                    this.endTime = RealmInstant.now()
                    this.date = LocalDate.now().toEpochMillis()


                }



                val bodyWeight = BodyWeight().apply {

                    this.weight = state.bodyWeight.toFloat()

                    val instant = Instant.ofEpochSecond(state.startTime.epochSeconds, state.startTime.nanosecondsOfSecond.toLong())
                    this.dateTime = ZonedDateTime.ofInstant(instant,ZoneId.of("UTC")).toEpochSecond()
                }

                copyToRealm(bodyWeight)

                copyToRealm(log)
            }
            RealmResponse.Success(Unit)
        }
        catch (e:Exception){

            RealmResponse.Error(e)
        }
    }
}

