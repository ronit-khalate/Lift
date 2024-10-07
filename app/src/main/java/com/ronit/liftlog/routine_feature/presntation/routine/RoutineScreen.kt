package com.ronit.liftlog.routine_feature.presntation.routine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ronit.liftlog.core.data.model.Exercise
import com.ronit.liftlog.core.domain.titlecase
import com.ronit.liftlog.core.presentation.component.BasicDialog
import com.ronit.liftlog.core.presentation.component.MaxWidthButton
import com.ronit.liftlog.core.presentation.component.SwipeToDeleteContainer
import com.ronit.liftlog.core.presentation.component.ThreeSectionTopBar
import com.ronit.liftlog.routine_feature.presntation.routine.components.ExerciseCard
import com.ronit.liftlog.routine_feature.presntation.routine.event.RoutineScreenEvent
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineScreen(
    modifier: Modifier = Modifier,
    routineId:String?=null,
    onNavigateToExerciseScreen:()->Unit,
    onBackBtnClicked:()->Unit,
    onExerciseClick:(exerciseId:String)->Unit,
    onDoneSavingRoutine:()->Unit,

) {

    val viewModel =
        hiltViewModel<RoutineScreenViewModel, RoutineScreenViewModel.RoutineScreenViewModelFactory> { factory ->
            factory.create(routineId)

        }

    var showExerciseListBottomSheet by rememberSaveable { mutableStateOf(false) }

    if(viewModel.state.dialogContent != null){

        BasicDialog(
            onDismiss = {viewModel.state.dialogContent!!.onDismiss?.invoke()},
            onConfirm = { viewModel.state.dialogContent!!.onConfirm?.invoke()},
            dismissBtnText = viewModel.state.dialogContent!!.dismissBtnText ,
            confirmBtnText = viewModel.state.dialogContent!!.confirmBtnText,
            titleText = viewModel.state.dialogContent!!.title,
            textString = viewModel.state.dialogContent!!.text
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
                    IconButton(onClick = {

                        viewModel.onEvent(RoutineScreenEvent.OnDoneBtnClicked(onDoneSavingRoutine))
                    }) {
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
            Column(
                modifier = Modifier
                    .fillMaxSize()

                ,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        textStyle = MaterialTheme.typography.headlineSmall.copy(color = primaryText),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        value = viewModel.state.routineName.titlecase(),
                        onValueChange = {
                            viewModel.onEvent(
                                RoutineScreenEvent.OnRoutineNameEntered(
                                    it
                                )
                            )
                        },
                        decorationBox = {

                            if (viewModel.state.routineName.isEmpty() || viewModel.state.routineName.isBlank()) {

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


                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .weight(1f)
                ) {

                    items(items = viewModel.state.exerciseList, key = { it._id.toHexString() }) {

                        SwipeToDeleteContainer(
                            onDelete = { viewModel.onEvent(RoutineScreenEvent.OnRemoveExercise(it._id.toHexString()))},
                            dialogTextText = "Are you sure you want to delete this exercise?",
                            dialogTitleText = "This action cannot be undone",
                            content = {
                                ExerciseCard(
                                    exerciseName = it.name,
                                    muscleGroup = it.muscleGroup ?: "",
                                    onClick = { onExerciseClick(it._id.toHexString()) },
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }


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


                    viewModel.onEvent(
                        RoutineScreenEvent.OnExerciseAdded(exerciseList)
                    )

                    showExerciseListBottomSheet = !showExerciseListBottomSheet


                },
                onCreateNewExercise = onNavigateToExerciseScreen
            )
        }

    }





