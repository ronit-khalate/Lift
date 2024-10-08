package com.ronit.liftlog.core.data.model.entity

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
    var category: String=""
    var equipment: String=""
    var force: String=""
    var remoteId: String=""
    var images: RealmList<String> = realmListOf()
    var instructions: RealmList<String> = realmListOf()
    var level: String=""
    var mechanic: String=""
    var primaryMuscles: RealmList<String> = realmListOf()
    var secondaryMuscles: RealmList<String> = realmListOf()


}