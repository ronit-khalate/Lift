package com.example.liftlog.core.data.mappers

import androidx.compose.foundation.gestures.snapping.SnapPosition
import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.data.model.ExerciseLog
import com.example.liftlog.core.data.model.Log
import com.example.liftlog.core.data.model.Routine
import com.example.liftlog.core.data.model.Set
import com.example.liftlog.core.domain.dto.ExerciseDto
import com.example.liftlog.routine_feature.domain.model.RoutineDto
import com.example.liftlog.start_routine_feature.presentation.state.StartRoutineScreenState
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId


fun Exercise.toExerciseDto(): ExerciseDto {

    return ExerciseDto(
        _id = this._id.toHexString(),
        name = this.name,
        Note = this.note,
        muscleGroup = this.muscleGroup
    )
}


fun ExerciseDto.toExercise():Exercise{
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


fun StartRoutineScreenState.toLog(): Log {
    return Log().apply {
        val l = realmListOf<ExerciseLog>()
        l.addAll(this@toLog.exercisesLog)
        this.exercisesLog = l

        this.routineId = this@toLog.routine?._id
        this.routineName = this@toLog.routine?.name ?: ""
        this.bodyWeight = try {
            this@toLog.bodyWeight.toFloat()
        } catch (e: Exception) {
            0.0F
        }

        this.endTime = RealmInstant.now()
    }
}


fun <T>List<T>.toRealmList(): RealmList<T> {

    val realmlist = realmListOf<T>()

    this.forEach {
        realmlist.addAll(this)
    }

    return realmlist
}

