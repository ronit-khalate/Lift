package com.example.liftlog.core.data.mappers

import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.presentation.exercise.dto.ExerciseDto


fun Exercise.toExerciseDto(): ExerciseDto {

    return ExerciseDto(
        _id = this._id.toString(),
        name = this.name,
        Note = this.Note,
        muscleGroup = this.muscleGroup
    )
}