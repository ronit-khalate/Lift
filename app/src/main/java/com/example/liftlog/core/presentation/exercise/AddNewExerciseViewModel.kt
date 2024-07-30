package com.example.liftlog.core.presentation.exercise

import androidx.lifecycle.ViewModel
import com.example.liftlog.core.domain.repository.ExerciseRepositoryImpl
import javax.inject.Inject


class AddNewExerciseViewModel @Inject constructor(
    private val repositoryImpl: ExerciseRepositoryImpl
):ViewModel() {



}