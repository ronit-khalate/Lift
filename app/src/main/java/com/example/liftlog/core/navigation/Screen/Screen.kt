package com.example.liftlog.core.navigation.Screen

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen( val route:String ){


    class RoutineScreen():Screen("routines_screen/{$ROUTINE_ID}"){

        companion object{
           const val ROUTINE_ID:String= "route_id"
        }



        operator fun invoke(routeId: String):String ="${this.route}/{$routeId}"
        val arguments = listOf(navArgument(ROUTINE_ID){type= NavType.StringType})

    }

    data object RoutinesScreen:Screen("routine_screen"){

        operator fun invoke():String = this.route

    }

    class ExerciseScreen():Screen("exercise_screen/{$EXERCISE_ID}"){

        companion object{
           const val EXERCISE_ID :String="exercise_id"
        }

        operator fun invoke(exercise:String?=null) ="${this.route}/{$exercise}"
    }


}


inline fun String.appendParams(vararg params:Pair<String,Any?>):String{

    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg->
            builder.append("/$arg")
        }
    }

    return  builder.toString()
}