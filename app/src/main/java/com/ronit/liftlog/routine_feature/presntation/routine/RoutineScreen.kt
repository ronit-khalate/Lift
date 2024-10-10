package com.ronit.liftlog.routine_feature.presntation.routine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ronit.liftlog.core.data.model.entity.Exercise
import com.ronit.liftlog.core.data.remote.ExerciseListService
import com.ronit.liftlog.core.presentation.component.BasicDialog
import com.ronit.liftlog.core.presentation.component.SwipeToDeleteContainer
import com.ronit.liftlog.core.presentation.component.ThreeSectionTopBar
import com.ronit.liftlog.routine_feature.presntation.routine.components.ExerciseCard
import com.ronit.liftlog.routine_feature.presntation.routine.event.RoutineScreenEvent
import com.ronit.liftlog.routine_feature.presntation.routine.state.RoutineScreenState
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText


/*
* Sate
*   Name
*   note
*
* Event
*   onAddExercie
*   OnNameChange
*   onStartRoutine
*   onAddExercise
*   onBackPress
*
*
* */


@Composable
fun RoutineScreen(
    modifier: Modifier = Modifier,
    state:RoutineScreenState,
    routineId:String?=null,
    onEvent:(RoutineScreenEvent)->Unit,
    onNavigateToExerciseScreen:()->Unit,
    onBackBtnClicked:()->Unit,
    onExerciseClick:(exerciseId:String)->Unit,
    onDoneSavingRoutine:()->Unit,

) {



    var showExerciseListBottomSheet by rememberSaveable { mutableStateOf(false) }

    val routineNameFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        routineNameFocusRequester.captureFocus()
    }


    if(state.dialogContent != null){

        BasicDialog(
            onDismiss = { state.dialogContent.onDismiss?.invoke()},
            onConfirm = { state.dialogContent.onConfirm?.invoke()},
            dismissBtnText = state.dialogContent.dismissBtnText ,
            confirmBtnText = state.dialogContent.confirmBtnText,
            titleText = state.dialogContent.title,
            textString = state.dialogContent.text
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            ThreeSectionTopBar(
                leftContent = {
                    IconButton(onClick = onBackBtnClicked) {
                        Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft, contentDescription = "")
                    }
                },

                middleContent = {

                    Text(
                        text = if(routineId != null)"Edit Routine" else "Create Routine",
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                },

                rightContent = {
                    IconButton(onClick = onDoneSavingRoutine) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "")
                    }
                }
            )
        },


        floatingActionButton = {

            Button(
                onClick = {  showExerciseListBottomSheet = !showExerciseListBottomSheet},
                colors = ButtonDefaults.buttonColors(containerColor = primary)
            ) {

                Text(
                    text = " Add Exercise",
                    style = MaterialTheme.typography.titleSmall,
                    color = black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) {paddingValues->



        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()

                ,
            contentAlignment = Alignment.BottomCenter
        ) {
            if(state.exerciseList.isEmpty()){

                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 200.dp),
                    text = "Add some Exercises here",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 22.sp),
                    color = body
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RoutineNameField(
                        state.routineName,
                        focusRequester = routineNameFocusRequester,
                        onRoutineNameChange = { onEvent(RoutineScreenEvent.OnRoutineNameEntered(it)) }
                    )
                }




                ExerciseList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .weight(1f),
                    list = state.exerciseList,
                    onExerciseClick=onExerciseClick,
                    onDelete = {onEvent(RoutineScreenEvent.OnRemoveExercise(it))}
                )



                Spacer(modifier = Modifier.height(16.dp))






            }


        }

    }

    AnimatedVisibility(
        visible = showExerciseListBottomSheet,
        enter = slideInVertically(
            animationSpec = tween(
                durationMillis = 300,
                easing = EaseOut
            ),
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            targetOffsetY = { it }
        )
    ) {


            ExerciseListScreen(
                routineId = routineId,
                onCancelBtnClicked = {
                    showExerciseListBottomSheet = !showExerciseListBottomSheet
                },
                onDoneAddingExercises = { exerciseList:List<Exercise> ->

                    onEvent(RoutineScreenEvent.OnExerciseAdded(exerciseList))
                    showExerciseListBottomSheet = !showExerciseListBottomSheet
                },
                onCreateNewExercise = onNavigateToExerciseScreen
            )
        }

    }



@Composable
internal fun RoutineNameField(
    routineName: String,
    focusRequester: FocusRequester,
    onRoutineNameChange: (String) -> Unit
) {

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester = focusRequester)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),

        textStyle = MaterialTheme.typography.headlineSmall.copy(color = primaryText),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        value = routineName,
        cursorBrush = SolidColor(primaryText),
        onValueChange = onRoutineNameChange,
        decorationBox = {
            if (routineName.isEmpty() || routineName.isBlank()) {
                Text(
                    text = "Routine Name",
                    style = MaterialTheme.typography.headlineSmall,
                    color = body
                )
            }
            it()
        }
    )
}

@Composable
internal fun ExerciseList(
    modifier: Modifier = Modifier,
    list: List<Exercise>,
    onDelete:(String)->Unit,
    onExerciseClick:(String)->Unit
) {

    LazyColumn(
        modifier = modifier
    ) {

        items(items = list, key = { it._id.toHexString() }) {

            SwipeToDeleteContainer(
                onDelete = {onDelete(it._id.toHexString())},
                dialogTextText = "Are you sure you want to delete this exercise?",
                dialogTitleText = "This action cannot be undone",
                content = {
                    ExerciseCard(
                        exercise = it,
                        muscleGroup = it.primaryMuscles,
                        onClick = { onExerciseClick(it._id.toHexString()) },
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}



