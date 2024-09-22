package com.ronit.liftlog.routine_feature.data

import com.ronit.liftlog.core.data.model.Routine
import com.ronit.liftlog.routine_feature.domain.repository.RoutineListRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


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