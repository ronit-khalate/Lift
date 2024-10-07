package com.ronit.liftlog.routine_feature.presntation.routine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronit.liftlog.core.data.model.Exercise
import com.ronit.liftlog.core.data.model.Routine
import com.ronit.liftlog.core.domain.repository.ExerciseRepositoryImpl
import com.ronit.liftlog.core.presentation.component.DialogContent
import com.ronit.liftlog.routine_feature.data.RoutineDetailRepositoryImpl
import com.ronit.liftlog.routine_feature.presntation.routine.event.RoutineScreenEvent
import com.ronit.liftlog.routine_feature.presntation.routine.state.RoutineScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.DeletedList
import io.realm.kotlin.notifications.DeletedObject
import io.realm.kotlin.notifications.InitialList
import io.realm.kotlin.notifications.InitialObject
import io.realm.kotlin.notifications.PendingObject
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.notifications.UpdatedList
import io.realm.kotlin.notifications.UpdatedObject
import kotlinx.coroutines.launch
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


            routineDetailRepositoryImpl.getRoutineInfo(id).collect{changes:SingleQueryChange<Routine>->

               when(changes){

                  is InitialObject -> {
                     state= RoutineScreenState(
                        routineId = changes.obj._id,
                        routineName = changes.obj.name,
                        note = changes.obj.note,
                        exerciseList = listOf(*changes.obj.exercise.toTypedArray())
                     )
                  }
                  is DeletedObject -> TODO()

                  is UpdatedObject -> {
                     state= RoutineScreenState(
                        routineId = changes.obj._id,
                        routineName = changes.obj.name,
                        note = changes.obj.note,
                        exerciseList = listOf(*changes.obj.exercise.toTypedArray())
                     )
                  }
                  is PendingObject -> TODO()
               }

            }


         }
         viewModelScope.launch {

            routineDetailRepositoryImpl.getExerciseChangeNotification(ObjectId(id)).collect{changes->

               when(changes){
                  is DeletedList -> TODO()
                  is InitialList -> {}
                  is UpdatedList -> {
                     state=state.copy(
                        exerciseList = changes.list
                     )
                  }
               }
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

         is RoutineScreenEvent.OnRemoveExercise -> {



            viewModelScope.launch {
               state=state.copy(
                  exerciseList = state.exerciseList.filter {
                     it._id.toHexString() != event.id
                  }
               )
               routineDetailRepositoryImpl.updateRoutine(state)
            }

         }
      }
   }

   private fun onNewExercisesAdded(exerciseList: List<Exercise>){

      viewModelScope.launch {





         state = state.copy(
            exerciseList =   exerciseList
         )



      }
   }

   private fun saveRoutine(onComplete:()->Unit){

      viewModelScope.launch {


         if(state.routineName.isBlank()){

            state = state.copy(
               dialogContent = DialogContent(
                  title = "Enter Routine Name",
                  text = "Please Enter Routine Name Before Saving",
                  onConfirm = {
                     state =state.copy(dialogContent = null)
                  },
                  confirmBtnText = "OK"
               )
            )
         }
         else {


            if (routineId != null) {

               routineDetailRepositoryImpl.updateRoutine(state.copy())
            } else {


               routineDetailRepositoryImpl.saveRoutine(

                  state


               )
            }
            onComplete()
         }



      }
   }





   @AssistedFactory
   interface RoutineScreenViewModelFactory{

      fun create(routineId:String?=null):RoutineScreenViewModel
   }
}