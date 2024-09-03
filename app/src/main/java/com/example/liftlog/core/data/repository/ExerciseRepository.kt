package com.example.liftlog.core.data.repository

import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.domain.dto.ExerciseDto
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmSingleQuery
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    suspend fun addExercise(exercise: Exercise):Unit
    suspend fun getAllExercises(): Flow<List<Exercise>>
    suspend fun getExercise(exerciseID:String): Exercise?

    suspend fun getAllRoutinesExercises(routineId:String):Array<Exercise>
}