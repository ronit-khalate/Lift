package com.example.liftlog.core.presentation.exercise.event

sealed interface ExerciseScreenEvent {
    data class OnNameChange(val name: String) : ExerciseScreenEvent
    data class OnMuscleGroupChange(val muscleGroup: String) : ExerciseScreenEvent
    data class OnNoteChange(val note: String) : ExerciseScreenEvent

    data class OnDoneBtnClicked(val onComplete:()->Unit):ExerciseScreenEvent
}