package com.ronit.liftlog.log_feature.domain

import com.ronit.liftlog.core.data.model.entity.Log
import com.ronit.liftlog.core.domain.toEpochMillis
import com.ronit.liftlog.log_feature.data.repository.LogRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class LogRepositoryImpl @Inject constructor(
    private val realm: Realm
):LogRepository {
    override suspend fun getAllLog(): Flow<List<Log>> {

     return realm.query<Log>().asFlow()
            .map {
                it.list
            }
    }

    override suspend fun getFirstLog(): Log? {

        val log = realm.query<Log>().find().minByOrNull {
            it.date
        }
        return log
    }

    override suspend fun getLog(date:LocalDate):List<Log>{

        return realm.query<Log>().find().filter {
            it.date == date.toEpochMillis()
        }
    }
}