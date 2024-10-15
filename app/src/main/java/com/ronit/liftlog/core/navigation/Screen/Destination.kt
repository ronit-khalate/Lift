package com.ronit.liftlog.core.navigation.Screen

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screens(val route:String ){


    data object ProfileScreen:Screens("profileScreen")
    data object LogScreen:Screens("LogScreen")
    data object StatScreen:Screens("statScreen")
    data object RoutineListScreen:Screens("routineListScreen")

     data object RoutineScreen:Screens("routineScreen/{routineID}"){

         const val ROUTINE_ID_ARGUMENT ="routineID"

        operator fun invoke(routeId: String? = null):String ="routineScreen/$routeId"
        val arguments = listOf(
            navArgument(ROUTINE_ID_ARGUMENT){
                type= NavType.StringType
                nullable =true
            }
        )

    }



    data object ExerciseScreen:Screens("exerciseScreen/{exerciseID}"){

        const val EXERCISE_ID_ARGUMENT:String ="exerciseID"

        val arguments = listOf(navArgument(EXERCISE_ID_ARGUMENT){
            type= NavType.StringType
            nullable=true
        })
        operator fun invoke(exercise:String?=null) ="exerciseScreen/${exercise}"
    }



    data object StartRoutineScreen:Screens("start_exercise_screen/{routine_id}/{routineName}"){

        const val ROUTINE_ID_ARGUMENT:String ="routine_id"
        const val ROUTINE_NAME_ARGUMENT ="routineName"

        val arguments = listOf(
            navArgument(ROUTINE_ID_ARGUMENT){
                type = NavType.StringType
                nullable = true
            },
            navArgument(ROUTINE_NAME_ARGUMENT){
                type= NavType.StringType
            },
        )




        operator fun invoke(routineId:String,routineName:String) = "start_exercise_screen/$routineId/$routineName"
    }



}



val highLevelDestinations = listOf(Screens.ProfileScreen,Screens.LogScreen,Screens.RoutineListScreen,Screens.StatScreen)

