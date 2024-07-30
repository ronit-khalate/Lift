package com.example.liftlog.core.presentation.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.domain.repository.ExerciseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListScreenViewModel @Inject constructor(
    private val exerciseListScreenRepository: ExerciseRepositoryImpl
):ViewModel() {


    lateinit var exerciseListFlow :MutableStateFlow<List<Exercise>>
        private set

    init {

        viewModelScope.launch {

            exerciseListScreenRepository.getAllExercises().collect{result->
                when(result){
                    is InitialResults -> {
                        exerciseListFlow.value= result.list
                    }
                    is UpdatedResults -> TODO("Handle UpdatedResults of exerciseListFlow")
                }
            }
        }
    }

}