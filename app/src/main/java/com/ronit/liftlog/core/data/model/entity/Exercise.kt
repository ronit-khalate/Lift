package com.ronit.liftlog.core.data.model.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import org.mongodb.kbson.ObjectId

class Exercise:RealmObject {

    var _id:ObjectId = ObjectId()
    @Index
    var name:String=""
    var note:String?=null
    @Index
    var muscleGroup:String?=null
    var setCount:Int =1
    var category: String=""
    @Index
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