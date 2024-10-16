package com.ronit.liftlog.core.data.model.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class Log: RealmObject {


    var _id:ObjectId = ObjectId()
    var routine:Routine?=null
    var startTime:RealmInstant = RealmInstant.now()
    var endTime:RealmInstant?=null
    var date:Long = 0L
    var workouts:RealmList<Workout> = realmListOf()

}