package com.example.liftlog.repository.ExerciseListRepository

import com.example.liftlog.data.Exercise
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmSingleQuery
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    suspend fun addExercise(exercise: Exercise):Unit
    suspend fun getAllExercises(): Flow<ResultsChange<Exercise>>
    suspend fun getExercise(exerciseID:String): RealmSingleQuery<Exercise>
}