package com.ronit.liftlog.routine_feature.presntation.routine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.layout.onSizeChanged
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


@OptIn(ExperimentalFoundationApi::class)
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


    val lazyListState = rememberLazyListState()

    var canShowRoutineNameInTopBar = remember {

        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            ThreeSectionTopBar(
                modifier = Modifier,
                leftContent = {
                    IconButton(onClick = onBackBtnClicked) {
                        Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft, contentDescription = "")
                    }
                },

                middleContent = {

                    AnimatedVisibility(
                        visible = canShowRoutineNameInTopBar.value,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {


                        Text(
                            text = state.routineName,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
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
        },
        floatingActionButtonPosition = FabPosition.End
    ) {paddingValues->




        Box(
            modifier = modifier
                .padding(paddingValues)
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
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



                LazyColumn(
                    modifier = modifier
                        .overscroll(ScrollableDefaults.overscrollEffect()),
                    state = lazyListState,
                ) {


                    item {
                        RoutineNameField(
                            modifier = Modifier
                                .padding(bottom = 16.dp),
                            routineName = state.routineName,
                            onRoutineNameChange = {
                                onEvent(
                                    RoutineScreenEvent.OnRoutineNameEntered(
                                        it
                                    )
                                )
                            },
                        )
                    }

                    items(items = state.exerciseList, key = { it._id.toHexString() }) {



                        SwipeToDeleteContainer(
                            onDelete = {onEvent(RoutineScreenEvent.OnRemoveExercise(it._id.toHexString()))},
                            dialogTextText = "This can't be undone",
                            dialogTitleText = "Delete Exercise?",
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

                    item {
                        Spacer(Modifier.height(32.dp))
                    }
                }









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
private fun RoutineNameField(
    modifier: Modifier=Modifier,
    routineName: String,
    onRoutineNameChange: (String) -> Unit
) {

    val routineNameFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        routineNameFocusRequester.captureFocus()
    }

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester = routineNameFocusRequester),

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


