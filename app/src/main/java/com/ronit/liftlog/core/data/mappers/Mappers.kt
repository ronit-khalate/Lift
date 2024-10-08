package com.ronit.liftlog.core.data.mappers

import com.ronit.liftlog.core.data.model.entity.Exercise
import com.ronit.liftlog.core.data.model.entity.Routine
import com.ronit.liftlog.core.domain.dto.ExerciseDto
import com.ronit.liftlog.routine_feature.domain.model.RoutineDto
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import org.mongodb.kbson.ObjectId


fun Exercise.toExerciseDto(): ExerciseDto {

    return ExerciseDto(
        _id = this._id.toHexString(),
        name = this.name,
        Note = this.note,
        muscleGroup = this.muscleGroup
    )
}


fun ExerciseDto.toExercise(): Exercise {
    return Exercise().apply {

        if(this@toExercise._id != null){

            _id = ObjectId(this@toExercise._id)
        }


        name = this@toExercise.name
        note = this@toExercise.Note
        muscleGroup = this@toExercise.muscleGroup

    }
}


fun Routine.toRoutineDto():RoutineDto{

    return  RoutineDto(
        _id = this._id.toHexString(),
        name = this.name,
        exercise = this.exercise.map {
            it.toExerciseDto()
        },
        date = this.date.toString()
    )
}


//fun StartRoutineScreenState.toLog(): Log {
//    return Log().apply {
//        val l = realmListOf<ExerciseLog>()
//        l.addAll(this@toLog.exercisesLog)
//        this.exercisesLog = l
//
//        this.routineId = this@toLog.routine?._id
//        this.routineName = this@toLog.routine?.name ?: ""
//        this.bodyWeight = try {
//            this@toLog.bodyWeight.toFloat()
//        } catch (e: Exception) {
//            0.0F
//        }
//
//        this.endTime = RealmInstant.now()
//    }
//}


fun <T>List<T>.toRealmList(): RealmList<T> {

    val realmlist = realmListOf<T>()
    realmlist.addAll(this)


    return realmlist
}

