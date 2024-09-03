package com.example.liftlog.routine_feature.domain.repository

import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.routine_feature.domain.model.RoutineDto
import com.example.liftlog.routine_feature.presntation.routine.state.RoutineScreenState
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.notifications.ListChange
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface RoutineDetailRepository {

    suspend fun getRoutineInfo(routineId:String): Flow<SingleQueryChange<Routine>>

    suspend fun addExerciseToRoutine(routineId: String,exerciseId:String)

    suspend fun saveRoutine(routine: RoutineScreenState)

    suspend fun updateRoutine(routine: RoutineScreenState)

    suspend fun getExerciseChangeNotification(routineId: ObjectId):Flow<ListChange<Exercise>>

}