package com.example.liftlog.core.data.repository

import com.example.liftlog.core.data.model.Exercise
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmSingleQuery
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    suspend fun addExercise(exercise: Exercise):Unit
    suspend fun getAllExercises(): Flow<ResultsChange<Exercise>>
    suspend fun getExercise(exerciseID:String): RealmSingleQuery<Exercise>
}