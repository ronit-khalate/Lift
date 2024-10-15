package com.ronit.liftlog.core.data.model.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import org.mongodb.kbson.ObjectId
import java.time.LocalDateTime

class Workout:RealmObject {

    var _id:ObjectId = ObjectId()
    var exerciseId:ObjectId?=null
    var exerciseName:String=""
    var startDateTime:Long=0L
    var endDateTime:Long=0L
    var sets:RealmList<Set> = realmListOf()

    @Ignore
    var previousSets:List<Set> = emptyList()

    fun updateSet(setId:ObjectId,update:Set.()->Set):Workout{

        val updatesSetList =this.sets

        val setToUpdate = updatesSetList.find { it._id == setId }



        setToUpdate?.let { set->
            val index= updatesSetList.indexOfFirst { it._id == set._id }

            if(index != -1){

                updatesSetList[index] = set.update()
            }
        }




        return Workout().apply {

            this._id = this@Workout._id
            this.exerciseId = this@Workout.exerciseId
            this.exerciseName = this@Workout.exerciseName
            this.startDateTime = this@Workout.startDateTime
            this.sets  = updatesSetList

        }
    }

    fun addSet():Workout{

        val updatesSetList =this.sets

        updatesSetList.add(
            Set().apply {
                this.setNo=this@Workout.sets.size+1
                this.exerciseId=this@Workout.exerciseId
            }
        )
        return Workout().apply {

            this._id = this@Workout._id
            this.exerciseId = this@Workout.exerciseId
            this.exerciseName = this@Workout.exerciseName
            this.startDateTime = this@Workout.startDateTime
            this.sets = updatesSetList

        }
    }
}