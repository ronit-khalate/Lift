package com.ronit.liftlog.core.data.model

import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class Exercise:RealmObject {

    var _id:ObjectId = ObjectId()
    var name:String=""
    var note:String?=null
    var muscleGroup:String?=null
    var setCount:Int =1


}