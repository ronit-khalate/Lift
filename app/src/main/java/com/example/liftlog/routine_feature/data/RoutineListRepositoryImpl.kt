package com.example.liftlog.routine_feature.data

import com.example.liftlog.core.data.mappers.toRoutineDto
import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.routine_feature.domain.model.RoutineDto
import com.example.liftlog.routine_feature.domain.repository.RoutineListRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.SingleQueryChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Qualifier


class RoutineListRepositoryImpl @Inject constructor(
    private val realm: Realm
):RoutineListRepository {
    override suspend fun getAllRoutine(): Flow<List<Routine>> {
        return realm.query<Routine>().asFlow().map {
           it.list.toList()
        }
    }

    override suspend fun addRoutine(routine: Routine) {
        realm.write {
            copyToRealm(routine)
        }
    }
}