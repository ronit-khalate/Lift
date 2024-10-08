package com.ronit.liftlog.routine_feature.data

import com.ronit.liftlog.core.data.model.entity.Exercise
import com.ronit.liftlog.core.data.model.entity.Routine
import com.ronit.liftlog.routine_feature.domain.repository.RoutineDetailRepository
import com.ronit.liftlog.routine_feature.presntation.routine.state.RoutineScreenState
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.notifications.ListChange
import io.realm.kotlin.notifications.SingleQueryChange
import kotlinx.coroutines.flow.Flow
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