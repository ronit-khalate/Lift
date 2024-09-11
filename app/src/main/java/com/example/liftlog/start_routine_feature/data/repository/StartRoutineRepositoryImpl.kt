package com.example.liftlog.start_routine_feature.data.repository

//import com.example.liftlog.core.data.mappers.toLog
import com.example.liftlog.core.data.mappers.toRealmList
import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.data.model.ExerciseLog
import com.example.liftlog.core.data.model.Log
import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.core.data.model.Set
import com.example.liftlog.core.domain.RealmResponse
import com.example.liftlog.routine_feature.data.RoutineDetailRepositoryImpl
import com.example.liftlog.start_routine_feature.domain.repository.StartRoutineRepository
import com.example.liftlog.start_routine_feature.presentation.state.StartRoutineScreenState
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.query.Sort
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import org.mongodb.kbson.ObjectId
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

            val log = result.first()

            val _result = realm.copyFromRealm(obj = log)

            RealmResponse.Success(data = _result)

        }
        catch (e:Exception){

            RealmResponse.Error(error = e)
        }
    }

    override suspend fun saveLog(state: StartRoutineScreenState): RealmResponse<Unit> {

        return try {

            realm.writeBlocking {

                val log = Log().apply {

                    val exLogList :List<ExerciseLog> = state.exercisesLog.map { exercisesLogDto->

                       ExerciseLog().apply {

                           this.exercise = query<Exercise>("_id == $0" , ObjectId(exercisesLogDto.exerciseID)).find().firstOrNull()
                           this.setList = exercisesLogDto.setList.map {
                               Set().apply {
                                   this.exercise =  query<Exercise>("_id == $0" , ObjectId(it.exerciseId)).find().firstOrNull()
                                   this.weight=it.weight
                                   this.repetitions= it.repetitions
                                   this.notes=it.notes
                               }
                           }.toRealmList()
                       }

                    }

                    this.exercisesLog.addAll(exLogList)



                    this.routineId = state.routine?._id
                    this.routineName = state.routine?.name ?: ""
                    this.bodyWeight = try {
                        state.bodyWeight.toFloat()
                    } catch (e: Exception) {
                        0.0F
                    }

                    this.date = state.date
                    this.endTime = RealmInstant.now()
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