package com.ronit.liftlog.start_routine_feature.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ronit.liftlog.core.presentation.component.BottomBar
import com.ronit.liftlog.core.presentation.component.SwipeToDeleteContainer
import com.ronit.liftlog.core.presentation.component.ThreeSectionTopBar
import com.ronit.liftlog.start_routine_feature.presentation.components.ExerciseSetLogCard
import com.ronit.liftlog.start_routine_feature.presentation.components.StartRoutineScreenTopBar
import com.ronit.liftlog.start_routine_feature.presentation.event.StartRoutineScreenEvent
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText

@Composable
fun StartRoutineScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    routineId:String,
    routineName:String

) {



    val viewmodel:StartRoutineViewModel = hiltViewModel<StartRoutineViewModel,StartRoutineViewModel.StartRoutineViewModelFactory>() {

        it.create(routineId,routineName)

    }

    val lazyListState = rememberLazyListState()

    val canShowRoutineNameInTopBar = remember {

        derivedStateOf {

            lazyListState.firstVisibleItemIndex>0
        }
    }



    Scaffold(
        modifier=modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar =  {

            ThreeSectionTopBar(
                leftContent = {
                    IconButton(
                        modifier = Modifier.weight(0.2f,false),
                        onClick = { navController.navigateUp() }
                    ) {

                        Image(
                            colorFilter = ColorFilter.tint(color = primaryText),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back"
                        )

                    }
                },

                middleContent = {

                    AnimatedVisibility(
                        modifier = Modifier.weight(0.8f,false),
                        visible = canShowRoutineNameInTopBar.value,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {


                        Text(
                            modifier = Modifier
                                .basicMarquee(),
                            text =viewmodel.routineName,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },

                rightContent = {

                    TextButton(
                        modifier = Modifier.weight(0.2f,false),
                        onClick = {
                            viewmodel.onEvent(StartRoutineScreenEvent.OnRoutineFinish)
                            navController.popBackStack()

                        },
                    ) {
                        Text(
                            text = "Stop",
                            style = MaterialTheme.typography.titleSmall.copy(color = primary, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            )

        },


    ) {paddingValues->


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .background(color = black),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                state = lazyListState
            ) {





                item {

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = viewmodel.routineName,
                            color = primaryText,
                            style = MaterialTheme.typography.headlineSmall
                        )

                    }
                }


                item {

                    Spacer(Modifier.height(8.dp))
                    BodyWeightTextField(
                        bodyWeight = viewmodel.state.bodyWeight,
                        onBodyWeightChange = {viewmodel.onEvent(StartRoutineScreenEvent.OnBodyWeightEntered(it))}
                    )
                    Spacer(Modifier.height(8.dp))
                }


                items(items = viewmodel.state.exercisesLog, key = {it.id.toHexString()}) {exerciseLog->





                        ExerciseSetLogCard(
                            exerciseLog = exerciseLog,
                            onAddSetBtnClick = {
                                viewmodel.onEvent(
                                    StartRoutineScreenEvent.OnAddSetInExerciseLog(
                                        it
                                    )
                                )
                            },
                            updateWeight = { id: String, exLogId, data: String ->

                                viewmodel.onEvent(
                                    StartRoutineScreenEvent.OnUpdateWeight(
                                        id = id,
                                        data = data,
                                        exLogId = exLogId
                                    )
                                )
                            },
                            updateReps = { id: String, exLogId, data: String ->

                                viewmodel.onEvent(
                                    StartRoutineScreenEvent.OnUpdateReps(
                                        id = id,
                                        data = data,
                                        exLogId = exLogId
                                    )
                                )

                            },
                            updateNotes = { id: String, exLogId, data: String ->
                                viewmodel.onEvent(
                                    StartRoutineScreenEvent.OnUpdateNotes(
                                        id = id,
                                        data = data,
                                        exLogId = exLogId
                                    )
                                )
                            }
                        )
                    



                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

        }
    }
}


@Composable
private fun BodyWeightTextField(
    modifier: Modifier=Modifier,
    bodyWeight: String,
    onBodyWeightChange: (String) -> Unit
) {



    BasicTextField(
        modifier = modifier
            .fillMaxWidth(),

        textStyle = MaterialTheme.typography.titleMedium.copy(color = primaryText),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Decimal),
        value = bodyWeight,
        cursorBrush = SolidColor(primaryText),
        onValueChange = onBodyWeightChange,
        decorationBox = {
            if (bodyWeight.isEmpty() || bodyWeight.isBlank()) {
                Text(
                    text = "Enter today's body weight",
                    style = MaterialTheme.typography.titleMedium,
                    color = body
                )
            }
            it()
        }
    )
}

@Preview(
    showBackground = true
)
@Composable
private fun StartRoutineScreenPreview() {
    StartRoutineScreen(routineId = "r",  navController = rememberNavController() , routineName = "")
}