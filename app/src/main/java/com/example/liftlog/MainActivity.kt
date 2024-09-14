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
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.liftlog.core.navigation.Screen.Screens
import com.example.liftlog.core.presentation.component.BottomBar
import com.example.liftlog.core.presentation.component.StickyBottomCard
import com.example.liftlog.core.presentation.exercise.ExerciseScreen
import com.example.liftlog.routine_feature.presntation.routine.RoutineScreen
import com.example.liftlog.routine_feature.presntation.routine_list.RoutineListScreen
import com.example.liftlog.start_routine_feature.domain.StartRoutineServiceManager
import com.example.liftlog.start_routine_feature.presentation.StartRoutineScreen
import com.example.liftlog.ui.theme.LiftLogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()))
        setContent {
            LiftLogTheme {


                val navController = rememberNavController()

                val currentBackStackEntry = navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStackEntry.value?.destination?.route




                Scaffold(
                    bottomBar = {

                        AnimatedVisibility(
                            visible = currentDestination == Screens.RoutineListScreen.route ||
                                    currentDestination == Screens.LogScreen.route ||
                                    currentDestination == Screens.StartScreen.route ||
                                    currentDestination == Screens.ProfileScreen.route,
                            enter = expandVertically(),

                        ){
                            BottomBar(navController = navController)
                        }




                    }
                )
                {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)

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
                                    onAddRoutine = {
                                        navController.navigate(Screens.RoutineScreen())
                                    },
                                    onCardClicked = {routineId ->
                                        navController.navigate(route = Screens.RoutineScreen.invoke(routineId))
                                    },
                                    onStartRoutineClicked ={ id,name->
                                        navController.navigate(route = Screens.StartRoutineScreen(routineId = id, routineName = name))
                                    } ,

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
                                    onExerciseClick = { exerciseId ->
                                        navController.navigate(Screens.ExerciseScreen(exerciseId))
                                    },
                                    onStartRoutineClicked = { id, name ->

                                        navController.popBackStack()
                                        navController.navigate(
                                            route = Screens.StartRoutineScreen.invoke(id, name)
                                        )
                                    }
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
                                StartRoutineScreen(
                                    routineId = routineID!!,
                                    routineName = routineName ?: "",
                                    navController = navController
                                )
                            }
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