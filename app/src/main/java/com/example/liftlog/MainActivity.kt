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
import com.example.liftlog.presentation.exercise.ExerciseScreen
import com.example.liftlog.presentation.routine.RoutineScreen
import com.example.liftlog.ui.theme.LiftLogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiftLogTheme {

                val navController = rememberNavController()
                NavHost(navController = navController , startDestination = Screen.RoutinesScreen()){


                    composable(route = Screen.RoutineScreen().route){
                        val routeId = it.arguments?.getString(Screen.RoutineScreen.ROUTINE_ID)

                        RoutineScreen(routineId = routeId)

                    }

                    composable(route = Screen.ExerciseScreen().route){

                        val exerciseId = it.arguments?.getString(Screen.ExerciseScreen.EXERCISE_ID)

                        ExerciseScreen()

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