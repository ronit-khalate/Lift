package com.example.liftlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.liftlog.core.navigation.Screen.Screen
import com.example.liftlog.core.presentation.exercise.ExerciseScreen
import com.example.liftlog.core.presentation.exercise.state.ExerciseScreenUseCaseState
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



                val navController = rememberNavController()
                NavHost(navController = navController , startDestination = Screen.RoutineListScreen()){


                    composable(route = Screen.RoutineListScreen()){


                        RoutineListScreen(
                            onAddRoutine = {
                                navController.navigate(Screen.RoutineScreen())
                            }
                        )

                    }

                    composable(
                        route = Screen.ExerciseScreen.route,
                        arguments = Screen.ExerciseScreen.arguments
                    ){

                        val exerciseId = it.arguments?.getString(Screen.ExerciseScreen.EXERCISE_ID_ARGUMENT)

                        ExerciseScreen(exerciseId = exerciseId, useCaseState = ExerciseScreenUseCaseState.NewExerciseUseCase)

                    }

                    composable(
                        route = Screen.RoutineScreen.route,
                        arguments = Screen.RoutineScreen.arguments
                    ){
                        val routineId = it.arguments?.getString(Screen.RoutineScreen.ROUTINE_ID_ARGUMENT)
                        RoutineScreen(
                            routineId = routineId
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