package com.example.liftlog.start_routine_feature.data.repository

import com.example.liftlog.core.data.model.Log
import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.core.domain.RealmResponse
import com.example.liftlog.routine_feature.data.RoutineDetailRepositoryImpl
import com.example.liftlog.start_routine_feature.domain.repository.StartRoutineRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import org.mongodb.kbson.ObjectId
import javax.inject.Inject


class StartRoutineRepositoryImpl @Inject constructor(
    private val realm:Realm,
): StartRoutineRepository {

    override suspend fun getRoutine(id: ObjectId): RealmResponse<Routine> {

       return try {
            val routine = realm.query<Routine>("_id == $0", id).find().first()

           RealmResponse.Success(routine)


       }
       catch (t:Throwable){

           RealmResponse.Error(t)
       }
    }

    override suspend fun getLastLogOfRoutineOrNull(routineId: ObjectId): RealmResponse<Log?> {

        return try {

            val result = realm.query<Log>("routineId == $0" , routineId).find()

            val log = result.firstOrNull()

            RealmResponse.Success(data = log)

        }
        catch (t:Throwable){

            RealmResponse.Error(error = t)
        }
    }

    override suspend fun saveLog(log: Log): RealmResponse<Unit> {

        return try {

            realm.writeBlocking {

                copyToRealm(log)
            }

            RealmResponse.Success(Unit)
        }
        catch (t:Throwable){

            RealmResponse.Error(t)
        }
    }
}