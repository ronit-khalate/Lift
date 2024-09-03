package com.example.liftlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.liftlog.core.navigation.Screen.Screens
import com.example.liftlog.routine_feature.presntation.routine.ExerciseListScreen
import com.example.liftlog.core.presentation.exercise.ExerciseScreen
import com.example.liftlog.routine_feature.presntation.routine.RoutineScreen
import com.example.liftlog.routine_feature.presntation.routine_list.RoutineListScreen
import com.example.liftlog.ui.theme.LiftLogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiftLogTheme {


                Scaffold(
                    modifier = Modifier
                ) {


                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier
                            .padding(it),
                        navController = navController,
                        startDestination = Screens.RoutineListScreen()
                    ) {


                        composable(route = Screens.RoutineListScreen()) {


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