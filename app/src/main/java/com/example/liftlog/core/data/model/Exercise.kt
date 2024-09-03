package com.example.liftlog.core.data.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class Exercise:RealmObject {

    var _id:ObjectId = ObjectId()
    var name:String=""
    var note:String?=null
    var muscleGroup:String?=null
    var setCount:Int =1
    var sets:RealmList<Set> = realmListOf()


}