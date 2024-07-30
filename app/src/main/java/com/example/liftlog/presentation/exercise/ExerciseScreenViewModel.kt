package com.example.liftlog.presentation.exercise

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.liftlog.presentation.exercise.state.ExerciseScreenState
import com.example.liftlog.repository.ExerciseListRepository.ExerciseRepositoryImpl
import javax.inject.Inject


class ExerciseScreenViewModel @Inject constructor(
    private val repositoryImpl: ExerciseRepositoryImpl
):ViewModel() {


    val state = mutableStateOf(ExerciseScreenState())

}