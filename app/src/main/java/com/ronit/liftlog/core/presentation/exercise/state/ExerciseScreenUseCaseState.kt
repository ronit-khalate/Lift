package com.ronit.liftlog.core.presentation.exercise.state

sealed interface ExerciseScreenUseCaseState {

    data object NewExerciseUseCase: ExerciseScreenUseCaseState

    data object ExistingExerciseUseCase: ExerciseScreenUseCaseState


}