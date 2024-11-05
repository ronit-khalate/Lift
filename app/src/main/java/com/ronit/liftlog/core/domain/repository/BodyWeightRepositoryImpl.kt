package com.ronit.liftlog.core.domain.repository

import com.ronit.liftlog.core.data.model.entity.BodyWeight
import com.ronit.liftlog.core.data.remote.BodyWeightRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class BodyWeightRepositoryImpl @Inject constructor(
    private val realm: Realm
):BodyWeightRepository {


    override suspend fun getLatestBodyWeight(): Float {

        val weight = realm.query<BodyWeight>().find().maxByOrNull { it.dateTime }?.weight ?: 0F
        return weight
    }

}