package com.ronit.liftlog.core.data.mappers

import android.util.Log
import com.ronit.liftlog.core.data.model.entity.Exercise
import com.ronit.liftlog.core.data.model.entity.Routine
import com.ronit.liftlog.core.data.model.response.ExerciseItemResponse
import com.ronit.liftlog.core.data.model.response.ExerciseListResponse
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

fun ExerciseItemResponse.toExercise():Exercise{

    Log.d("exerciseApi", this.toString())
    return  Exercise().apply {
        this._id=ObjectId()
        this.name=this@toExercise.name
        this.category=this@toExercise.category
        this.equipment=this@toExercise.equipment?:""
        this.force=this@toExercise.force?:""
        this.remoteId=this@toExercise.id
        this.images=this@toExercise.images.toRealmList()
        this.instructions = this@toExercise.instructions.toRealmList()
        this.level=this@toExercise.level
        this.mechanic = this@toExercise.mechanic?:""
        this.primaryMuscles = this@toExercise.primaryMuscles.toRealmList()
        this.secondaryMuscles=this@toExercise.secondaryMuscles.toRealmList()
    }
}


fun ExerciseListResponse.toExerciseList():RealmList<Exercise> {

    return  this.map { it.toExercise() }.toRealmList()
}


fun <T>List<T>.toRealmList(): RealmList<T> {

    val realmlist = realmListOf<T>()
    realmlist.addAll(this)


    return realmlist
}

