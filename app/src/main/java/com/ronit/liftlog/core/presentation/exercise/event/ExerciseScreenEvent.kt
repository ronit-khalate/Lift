package com.ronit.liftlog.core.presentation.exercise.event

sealed interface ExerciseScreenEvent {
    data class OnNameChange(val name: String) : ExerciseScreenEvent
    data class OnMuscleGroupChange(val idx: Int) : ExerciseScreenEvent
    data class OnNoteChange(val note: String) : ExerciseScreenEvent

    data class OnDoneBtnClicked(val onComplete:()->Unit):ExerciseScreenEvent

    data object OnDialogConfirmBtnClicked:ExerciseScreenEvent
    data object OnDialogDismissBtnClicked:ExerciseScreenEvent



}