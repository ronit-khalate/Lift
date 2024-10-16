package com.ronit.liftlog

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ronit.liftlog.core.data.remote.ExerciseListService
import com.ronit.liftlog.core.navigation.Screen.Screens
import com.ronit.liftlog.core.navigation.Screen.highLevelDestinations
import com.ronit.liftlog.core.presentation.component.BottomBar
import com.ronit.liftlog.core.presentation.component.StickyBottomCard
import com.ronit.liftlog.core.presentation.exercise.ExerciseScreen
import com.ronit.liftlog.core.presentation.exercise.ExerciseViewModel
import com.ronit.liftlog.core.presentation.exercise.event.ExerciseScreenEvent
import com.ronit.liftlog.log_feature.ui.LogScreen
import com.ronit.liftlog.log_feature.ui.LogViewModel
import com.ronit.liftlog.log_feature.ui.event.LogScreenUiEvent
import com.ronit.liftlog.routine_feature.presntation.routine.RoutineScreen
import com.ronit.liftlog.routine_feature.presntation.routine.RoutineScreenViewModel
import com.ronit.liftlog.routine_feature.presntation.routine.event.RoutineScreenEvent
import com.ronit.liftlog.routine_feature.presntation.routine_list.RoutineListScreen
import com.ronit.liftlog.start_routine_feature.domain.StartRoutineServiceManager
import com.ronit.liftlog.start_routine_feature.presentation.StartRoutineScreen
import com.ronit.liftlog.start_routine_feature.presentation.StartRoutineViewModel
import com.ronit.liftlog.start_routine_feature.presentation.event.StartRoutineScreenEvent
import com.ronit.liftlog.ui.theme.LiftLogTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var exerciseListService: ExerciseListService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            LiftLogTheme {

                val navController = rememberNavController()
                val currentBackStackEntry = navController.currentBackStackEntryAsState()
                val currentDestination = remember {

                    derivedStateOf {
                        currentBackStackEntry.value?.destination?.route
                    }
                }



                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {


                    NavHost(
                        modifier = Modifier
                            .fillMaxSize(),

                        navController = navController,
                        startDestination = Screens.RoutineListScreen.route
                    ) {


                        Log.d(
                            "current_des",
                            currentBackStackEntry.toString()
                        )
                        composable(route = Screens.RoutineListScreen.route) {


                            Log.d(
                                "current_des",
                                currentBackStackEntry.toString()
                            )
                            RoutineListScreen(
                                navController = navController,
                                onAddRoutine = {
                                    navController.navigate(Screens.RoutineScreen())
                                },
                                onCardClicked = { routineId ->
                                    navController.navigate(
                                        route = Screens.RoutineScreen.invoke(
                                            routineId
                                        )
                                    )
                                },
                                onStartRoutineClicked = { id, name ->
                                    navController.navigate(
                                        route = Screens.StartRoutineScreen(
                                            routineId = id,
                                            routineName = name
                                        )
                                    )
                                },

                                )

                        }

                        composable(
                            route = Screens.ExerciseScreen.route,
                            arguments = Screens.ExerciseScreen.arguments
                        ) {

                            val exerciseId =
                                it.arguments?.getString(Screens.ExerciseScreen.EXERCISE_ID_ARGUMENT)

                            val viewModel = hiltViewModel <ExerciseViewModel, ExerciseViewModel.ExerciseViewModelFactory>{ factory->

                                factory.create(exerciseId)

                            }

                            ExerciseScreen(
                                uiState = viewModel.exercise,
                                navController = navController,
                                muscleGroupIdx = viewModel.muscleGroupIdx,
                                onEvent = {event-> viewModel.onEvent(event)}
                            )

                        }

                        composable(
                            route = Screens.RoutineScreen.route,
                            arguments = Screens.RoutineScreen.arguments,

                        ) {
                            val routineId =
                                it.arguments?.getString(Screens.RoutineScreen.ROUTINE_ID_ARGUMENT)

                            val viewModel= hiltViewModel<RoutineScreenViewModel, RoutineScreenViewModel.RoutineScreenViewModelFactory> { factory ->
                                    factory.create(routineId)
                            }
                            RoutineScreen(
                                routineId = routineId,
                                state = viewModel.state,
                                onNavigateToExerciseScreen = {
                                    navController.navigate(Screens.ExerciseScreen()) {
                                        launchSingleTop = true
                                    }
                                },
                                onBackBtnClicked = {
                                    navController.navigateUp()
                                },
                                onExerciseClick = { exerciseId ->
                                    navController.navigate(Screens.ExerciseScreen(exerciseId))
                                },
                                onDoneSavingRoutine = {

                                    viewModel.onEvent(RoutineScreenEvent.OnDoneBtnClicked{
                                        navController.navigateUp()
                                    })

                                },
                                onEvent = {uiEvent -> viewModel.onEvent(uiEvent)},
                            )
                        }


                        composable(
                            route = Screens.StartRoutineScreen.route,
                            arguments = Screens.StartRoutineScreen.arguments
                        ) {

                            val routineID =
                                it.arguments?.getString(Screens.StartRoutineScreen.ROUTINE_ID_ARGUMENT)

                            val routineName =
                                it.arguments?.getString(Screens.StartRoutineScreen.ROUTINE_NAME_ARGUMENT)

                            val viewmodel: StartRoutineViewModel = hiltViewModel<StartRoutineViewModel, StartRoutineViewModel.StartRoutineViewModelFactory>() {

                                it.create(routineID!!,routineName?:"")

                            }
                            StartRoutineScreen(
                                routineId = routineID!!,
                                routineName = routineName ?: "",
                                uiState = viewmodel.state,
                                navController = navController,
                                onEvent = {event: StartRoutineScreenEvent -> viewmodel.onEvent(event)}
                            )
                        }


                        composable(
                            route = Screens.LogScreen.route,
                        ) {
                            val viewModel = hiltViewModel<LogViewModel>()

                            LogScreen(
                                state = viewModel.state,
                                navController = navController,
                                onEvent = { event:LogScreenUiEvent -> viewModel.onEvent(event)},

                            )
                        }
                    }



                    AnimatedVisibility(
                        modifier = Modifier
                            .align(Alignment.BottomCenter),
                        visible = (highLevelDestinations.any { it.route == currentDestination.value })
                                            && StartRoutineServiceManager.isRunning,
                        enter = slideInVertically(
                            initialOffsetY = {-it/2}
                        ),

                        exit = slideOutVertically(
                            targetOffsetY = { it}
                        ),

                    ) {

                            StickyBottomCard(
                                modifier = Modifier
                                    .padding(bottom =130.dp)
                                    .align(Alignment.BottomCenter),
                                routineName = StartRoutineServiceManager.routineName,
                                totalExercise = StartRoutineServiceManager.totalExercises,
                                onCardClick = {
                                    navController.navigate(
                                        Screens.StartRoutineScreen(
                                            routineId = StartRoutineServiceManager.state?.routine?._id?.toHexString()!!,
                                            routineName = StartRoutineServiceManager.state?.routine?.name
                                                ?: ""
                                        )
                                    )
                                },
                                onFinishBtnClick = {
                                    StartRoutineServiceManager.stopService()
                                }
                            )
                    }
                }




            }



        }


    }




}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LiftLogTheme {
        Greeting("Android")
    }
}