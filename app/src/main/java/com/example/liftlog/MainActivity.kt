package com.example.liftlog

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.liftlog.core.navigation.Screen.Screens
import com.example.liftlog.core.presentation.component.StickyBottomSheet
import com.example.liftlog.routine_feature.presntation.routine.ExerciseListScreen
import com.example.liftlog.core.presentation.exercise.ExerciseScreen
import com.example.liftlog.routine_feature.presntation.routine.RoutineScreen
import com.example.liftlog.routine_feature.presntation.routine_list.RoutineListScreen
import com.example.liftlog.start_routine_feature.domain.StartRoutineServiceManager
import com.example.liftlog.start_routine_feature.presentation.StartRoutineScreen
import com.example.liftlog.start_routine_feature.presentation.components.StartRoutineScreenTopBar
import com.example.liftlog.ui.theme.LiftLogTheme
import com.example.liftlog.ui.theme.black

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiftLogTheme {


                val navController = rememberNavController()

                val canShowStickyBottomSheetOnCurrentDestination  = navController.currentBackStackEntryAsState()

                Scaffold(
                    modifier = Modifier,

                    bottomBar = {


                        AnimatedVisibility(
                            visible = StartRoutineServiceManager.isServiceRunning() && (canShowStickyBottomSheetOnCurrentDestination.value?.destination?.route== Screens.RoutineListScreen.route),
                            enter = slideInVertically(
                                animationSpec = spring()
                            ),
                            exit = slideOutVertically(
                                animationSpec = tween()
                            )
                        ) {
                            StickyBottomSheet(modifier = Modifier.height(100.dp).padding(bottom = 100.dp))
                        }

                    }
                ) {



                    NavHost(
                        modifier = Modifier
                            .padding(it),
                        navController = navController,
                        startDestination = Screens.RoutineListScreen()
                    ) {


                        Log.d("current_des" , canShowStickyBottomSheetOnCurrentDestination.toString())
                        composable(route = Screens.RoutineListScreen()) {


                            Log.d("current_des" , canShowStickyBottomSheetOnCurrentDestination.toString())
                            RoutineListScreen(
                                onAddRoutine = {
                                    navController.navigate(Screens.RoutineScreen())
                                },
                                onRoutineClicked = {routineID->

                                    navController.navigate(route = Screens.RoutineScreen(routeId = routineID))
                                }
                            )

                        }

                        composable(
                            route = Screens.ExerciseScreen.route,
                            arguments = Screens.ExerciseScreen.arguments
                        ) {

                            val exerciseId =
                                it.arguments?.getString(Screens.ExerciseScreen.EXERCISE_ID_ARGUMENT)

                            ExerciseScreen(
                                exerciseId = exerciseId,
                                navController = navController
                            )

                        }

                        composable(
                            route = Screens.RoutineScreen.route,
                            arguments = Screens.RoutineScreen.arguments
                        ) {
                            val routineId =
                                it.arguments?.getString(Screens.RoutineScreen.ROUTINE_ID_ARGUMENT)
                            RoutineScreen(
                                routineId = routineId,
                                onNavigateToExerciseScreen = {
                                    navController.navigate(Screens.ExerciseScreen()) {
                                        launchSingleTop = true
                                    }
                                },
                                onBackBtnClicked = {
                                    navController.popBackStack()
                                },
                                onExerciseClick = {exerciseId->
                                    navController.navigate(Screens.ExerciseScreen(exerciseId))
                                },
                                onStartRoutineClicked = {

                                    navController.popBackStack()
                                    navController.navigate(route = Screens.StartRoutineScreen(it))
                                }
                            )
                        }


                        composable(
                            route = Screens.StartRoutineScreen.route,
                            arguments = Screens.StartRoutineScreen.arguments
                        ){

                            val routineID = it.arguments?.getString(Screens.StartRoutineScreen.ROUTINE_ID_ARGUMENT)
                            StartRoutineScreen(
                                routineId = routineID!!,
                                topbar = {
                                    StartRoutineScreenTopBar(
                                        onBackNavigate = {navController.navigateUp()},
                                        onFinishBtnClick = {navController.popBackStack()}
                                    )
                                }
                            )
                        }
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