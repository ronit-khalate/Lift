package com.ronit.liftlog.core.data.repository

import com.ronit.liftlog.core.data.model.entity.Workout
import io.realm.kotlin.query.RealmResults
import org.mongodb.kbson.ObjectId

interface WorkoutRepository {


    suspend fun getWorkout(id:ObjectId):RealmResults<Workout>
    suspend fun getWorkouts(ids:List<ObjectId>):List<Workout>
    suspend fun getLatestWorkoutOfExercise(exerciseId: ObjectId):Workout?
    suspend fun getLatestWorkoutsOfExercises(exerciseIds: List<ObjectId>):List<Workout>
}