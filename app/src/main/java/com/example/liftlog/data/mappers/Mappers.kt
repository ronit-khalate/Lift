package com.example.liftlog.data.mappers

import com.example.liftlog.data.Exercise
import com.example.liftlog.presentation.exercise.dto.ExerciseDto
import io.realm.kotlin.internal.platform.returnType
import io.realm.kotlin.internal.platform.threadId


fun Exercise.toExerciseDto():ExerciseDto{

    return ExerciseDto(
        _id = this._id.toString(),
        name = this.name,
        Note = this.Note,
        muscleGroup = this.muscleGroup
    )
}