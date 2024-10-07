package com.ronit.liftlog.core.data.repository

import com.ronit.liftlog.core.data.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {


    suspend fun getAllExercises(): Flow<List<Exercise>>
    suspend fun getExercise(exerciseID:String): Exercise?
    suspend fun upsertExercise(exercise: Exercise)

    suspend fun getAllRoutinesExercises(routineId:String):Array<Exercise>
}