package com.ronit.liftlog.log_feature.data.repository

import com.ronit.liftlog.core.data.model.entity.Log
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface LogRepository {

    suspend fun getAllLog(): Flow<List<Log>>

    suspend fun getFirstLog(): Log?

    suspend fun getLog(date: LocalDate):List<Log>
}