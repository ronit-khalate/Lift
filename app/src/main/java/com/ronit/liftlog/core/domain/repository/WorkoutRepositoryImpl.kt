package com.ronit.liftlog.core.domain.repository

import com.ronit.liftlog.core.data.model.entity.Workout
import com.ronit.liftlog.core.data.repository.WorkoutRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val realm:Realm
):WorkoutRepository {
    override suspend fun getWorkout(id: ObjectId): RealmResults<Workout> {
        return realm.query<Workout>("_id == $0" , id).find()
    }

    override suspend fun getWorkouts(ids: List<ObjectId>): List<Workout> {

        val workouts = mutableListOf<Workout>()

        for(id in ids){

            val workout = realm.query<Workout>("_id == $0" , id).find().firstOrNull()?:continue

            workouts.add(workout)
        }

        return  workouts
    }

    override suspend fun getLatestWorkoutOfExercise(exerciseId: ObjectId): Workout? {

        val workout =realm.query<Workout>("exerciseId == $0", exerciseId).find().maxByOrNull { it.startDateTime }
        return workout
    }

    override suspend fun getLatestWorkoutsOfExercises(exerciseIds: List<ObjectId>): List<Workout> {

        val workouts = mutableListOf<Workout>()

        for (id in exerciseIds){

            val workout = realm.query<Workout>("exerciseId == $0" , id).find().maxByOrNull { it.startDateTime }?:continue

            workouts.add(workout)
        }

        return workouts
    }
}