package com.example.liftlog.presentation.exercise.state

import com.example.liftlog.data.Exercise

data class ExerciseScreenState(
    val exercise: Exercise,
    val useCaseState: ExerciseScreenUseCaseState = ExerciseScreenUseCaseState.NewExerciseUseCase
)