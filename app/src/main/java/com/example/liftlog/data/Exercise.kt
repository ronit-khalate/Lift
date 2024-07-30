package com.example.liftlog.data

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class Exercise:RealmObject {

    var _id:ObjectId = ObjectId()
    var name:String=""
    var Note:String?=null
    var muscleGroup:String?=null
    var sets:RealmList<Set> = realmListOf()


}