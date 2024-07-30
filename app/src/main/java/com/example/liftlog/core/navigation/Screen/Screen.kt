package com.example.liftlog.core.navigation.Screen

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.liftlog.core.navigation.Screen.Screen.RoutineScreen.ROUTINE_ID_ARGUMENT

sealed class Screen(  val route:String ){



     data object RoutineScreen:Screen("routineScreen/{routineID}"){

         const val ROUTINE_ID_ARGUMENT ="routineID"
        operator fun invoke(routeId: String?=null):String ="routineScreen/{$routeId}"
        val arguments = listOf(navArgument(ROUTINE_ID_ARGUMENT){type= NavType.StringType})

    }

    data object RoutineListScreen:Screen("routineScreen"){


        const val EXERCISE_ID_ARGUMENT:String ="exerciseID"

        operator fun invoke():String = this.route

    }

    data object ExerciseScreen:Screen("exerciseScreen/"){

        const val EXERCISE_ID_ARGUMENT:String ="exerciseID"

        val arguments = listOf(navArgument(EXERCISE_ID_ARGUMENT){type= NavType.StringType})
        operator fun invoke(exercise:String?=null) ="exerciseScreen/{$exercise}"
    }


}

