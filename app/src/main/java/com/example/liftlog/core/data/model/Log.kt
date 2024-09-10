package com.example.liftlog.core.data.model

import io.realm.kotlin.ext.realmDictionaryOf
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmDictionary
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmMap
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class Log: RealmObject {


    var _id:ObjectId = ObjectId()
    var routineName: String =""
    var routineId:ObjectId?=null
    var startTime:RealmInstant = RealmInstant.now()
    var endTime:RealmInstant?=null
    var date:Long = 0L
    var bodyWeight:Float=0.0f

    var exercisesLog:RealmList<ExerciseLog> = realmListOf()

}