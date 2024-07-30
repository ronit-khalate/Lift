package com.example.liftlog.core.presentation.exercise.state

import com.example.liftlog.core.data.model.Exercise

data class ExerciseScreenState(
    val exercise: Exercise,
    val useCaseState: ExerciseScreenUseCaseState = ExerciseScreenUseCaseState.NewExerciseUseCase
)