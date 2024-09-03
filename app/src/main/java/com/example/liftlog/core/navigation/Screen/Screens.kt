package com.example.liftlog.core.navigation.Screen

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screens(val route:String ){



     data object RoutineScreen:Screens("routineScreen/{routineID}"){

         const val ROUTINE_ID_ARGUMENT ="routineID"
        operator fun invoke(routeId: String?=null):String ="routineScreen/$routeId"
        val arguments = listOf(
            navArgument(ROUTINE_ID_ARGUMENT){
                type= NavType.StringType
                nullable =true
            })

    }

    data object RoutineListScreen:Screens("routineScreen"){


        const val EXERCISE_ID_ARGUMENT:String ="exerciseID"

        operator fun invoke():String = this.route

    }

    data object ExerciseScreen:Screens("exerciseScreen/{exerciseID}"){

        const val EXERCISE_ID_ARGUMENT:String ="exerciseID"

        val arguments = listOf(navArgument(EXERCISE_ID_ARGUMENT){
            type= NavType.StringType
            nullable=true
        })
        operator fun invoke(exercise:String?=null) ="exerciseScreen/${exercise}"
    }

    data object ExerciseListScreen:Screens("exerciseListScreen"){



        enum class USECASE{

            ADD_EXERCISE_TO_ROUTINE,
            EXPLORE_EXERCISES
        }


    }



}

