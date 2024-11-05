package com.ronit.liftlog.core.data.remote

interface BodyWeightRepository {

    suspend fun getLatestBodyWeight():Float
}