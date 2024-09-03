package com.example.liftlog.routine_feature.presntation.routine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.core.domain.dto.ExerciseDto
import com.example.liftlog.core.domain.repository.ExerciseRepositoryImpl
import com.example.liftlog.routine_feature.data.RoutineDetailRepositoryImpl
import com.example.liftlog.routine_feature.domain.model.RoutineDto
import com.example.liftlog.routine_feature.presntation.routine.event.RoutineScreenEvent
import com.example.liftlog.routine_feature.presntation.routine.state.RoutineScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

@HiltViewModel(assistedFactory = RoutineScreenViewModel.RoutineScreenViewModelFactory::class)
class RoutineScreenViewModel @AssistedInject constructor(
   @Assisted private val routineId:String?,
   private val routineDetailRepositoryImpl: RoutineDetailRepositoryImpl,
   private val exerciseRepositoryImpl: ExerciseRepositoryImpl
):ViewModel() {





   var state  by mutableStateOf(RoutineScreenState())
      private set



   init {

      routineId?.let { id->

         viewModelScope.launch {

            routineDetailRepositoryImpl.getRoutineInfo(id)?.let { routine:Routine ->

               state= RoutineScreenState(
                  routineId = routine._id,
                  routineName = routine.name,
                  note = routine.note,
                  exerciseList = routine.exercise
               )
            }


         }
      }
   }


   fun onEvent(event: RoutineScreenEvent){

      when(event){
         is RoutineScreenEvent.OnExerciseAdded -> {


            onNewExercisesAdded(event.map)
         }

         is RoutineScreenEvent.OnDoneBtnClicked -> {
            saveRoutine(
               onComplete = event.onCompleteUpserting
            )
         }
         is RoutineScreenEvent.OnRoutineNameEntered -> {

            state=state.copy(routineName = event.name)
         }
         is RoutineScreenEvent.OnRoutineNoteEntered -> {

            state=state.copy(note = event.note)
         }
         is RoutineScreenEvent.OnRoutineStartWorkOutClicked -> {

         }
      }
   }

   private fun onNewExercisesAdded(exerciseList: List<Exercise>){

      viewModelScope.launch {



         state = state.copy(
            exerciseList = exerciseList.toRealmList()
         )



      }
   }

   private fun saveRoutine(onComplete:()->Unit){

      viewModelScope.launch {



         if(routineId != null) {

            routineDetailRepositoryImpl.updateRoutine(state.copy())
         }
         else{

            
            routineDetailRepositoryImpl.saveRoutine(

              state



            )
         }

         onComplete()

      }
   }





   @AssistedFactory
   interface RoutineScreenViewModelFactory{

      fun create(routineId:String?=null):RoutineScreenViewModel
   }
}