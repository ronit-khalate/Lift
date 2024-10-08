package com.ronit.liftlog.core.data.remote

import com.ronit.liftlog.core.data.model.response.ExerciseListResponse
import retrofit2.http.GET

interface ExerciseListService {

    @GET("dist/exercises.json")
    suspend fun getExerciseList():ExerciseListResponse
}