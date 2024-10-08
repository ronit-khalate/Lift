package com.ronit.liftlog.core.data.repository

import com.ronit.liftlog.core.data.model.entity.Exercise
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {


    suspend fun getAllExercises(): Flow<List<Exercise>>
    suspend fun getExercise(exerciseID:String): Exercise?
    suspend fun upsertExercise(exercise: Exercise)

    suspend fun upsertAllExercises(exercises:RealmList<Exercise>)

    suspend fun getAllExerciseCount():Int

    suspend fun getAllRoutinesExercises(routineId:String):Array<Exercise>
}