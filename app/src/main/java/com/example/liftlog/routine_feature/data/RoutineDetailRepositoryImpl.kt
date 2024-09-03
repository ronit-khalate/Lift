package com.example.liftlog.routine_feature.data

import com.example.liftlog.core.data.mappers.toExercise
import com.example.liftlog.core.data.mappers.toRealmList
import com.example.liftlog.core.data.mappers.toRoutineDto
import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.routine_feature.domain.model.RoutineDto
import com.example.liftlog.routine_feature.domain.repository.RoutineDetailRepository
import com.example.liftlog.routine_feature.presntation.routine.state.RoutineScreenState
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.notifications.ListChange
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class RoutineDetailRepositoryImpl @Inject  constructor(
    private val realm: Realm
): RoutineDetailRepository {
    override suspend fun getRoutineInfo(routineId: String): Flow<SingleQueryChange<Routine>> {
        return realm.query<Routine>("_id == $0",ObjectId(routineId)).first().asFlow()
    }

    override suspend fun addExerciseToRoutine(routineId: String, exerciseId:String) {
        realm.writeBlocking {

            val routine = query<Routine>("_id == $routineId").find().first()
            val exercise = query<Exercise>("_id == $exerciseId").find().first()

            routine.exercise.add(exercise)
        }
    }

    override suspend fun saveRoutine(routine:RoutineScreenState) {



        realm.writeBlocking {


            val exercises = realmListOf(
                *routine.exerciseList.map {
                    findLatest(it)!!
                }.toTypedArray()

            )


            val rout = Routine().apply {
                this.name=routine.routineName
                this.note=routine.note
                this.exercise= exercises

            }
            copyToRealm(rout)

        }
    }

    override suspend fun updateRoutine(routine: RoutineScreenState) {



        realm.writeBlocking {



            val _routine = query<Routine>("_id==$0",routine.routineId).find().first()




            findLatest(_routine)?.let {

               _routine.name = routine.routineName
                _routine.note = routine.note
                _routine.exercise = realmListOf(
                    *routine.exerciseList.map {
                        findLatest(it)!!
                    }.toTypedArray()

                )
            }



        }
    }

    override suspend fun getExerciseChangeNotification(routineId: ObjectId): Flow<ListChange<Exercise>> {

        return realm.query<Routine>("_id==$0",routineId).find().first().exercise.asFlow()
    }


}