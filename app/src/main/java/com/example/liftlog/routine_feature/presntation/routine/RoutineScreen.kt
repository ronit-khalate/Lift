package com.example.liftlog.routine_feature.presntation.routine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.presentation.component.MaxWidthButton
import com.example.liftlog.routine_feature.presntation.routine_list.components.RoutineCard
import com.example.liftlog.core.presentation.component.ThreeSectionTopBar
import com.example.liftlog.routine_feature.presntation.routine.components.ExerciseCard
import com.example.liftlog.routine_feature.presntation.routine.event.RoutineScreenEvent
import com.example.liftlog.ui.theme.body
import com.example.liftlog.ui.theme.neutral
import com.example.liftlog.ui.theme.primary
import com.example.liftlog.ui.theme.tertiary


/*
*
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
    onStartRoutineClicked:(id:String,name:String)->Unit

) {

    val viewModel =
        hiltViewModel<RoutineScreenViewModel, RoutineScreenViewModel.RoutineScreenViewModelFactory> { factory ->
            factory.create(routineId)

        }

    var showExerciseListBottomSheet by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = MaterialTheme.typography.headlineSmall.copy(color = primary),

                    value = viewModel.state.routineName,
                    onValueChange = { viewModel.onEvent(RoutineScreenEvent.OnRoutineNameEntered(it)) },
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


            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                items(items = viewModel.state.exerciseList, key = {it._id.toHexString()}) {

                    ExerciseCard(
                        exerciseName = it.name,
                        muscleGroup = it.muscleGroup?:"",
                        onClick = { onExerciseClick(it._id.toHexString()) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            MaxWidthButton(onClick = {showExerciseListBottomSheet = !showExerciseListBottomSheet}, text = "+  Add Exercise")

            Spacer(modifier = Modifier.height(15.dp))


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





