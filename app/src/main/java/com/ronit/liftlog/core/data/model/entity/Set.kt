package com.ronit.liftlog.core.data.model.entity


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class Set:RealmObject {

    @PrimaryKey
    var _id:ObjectId = ObjectId()
    var setNo:Int = 0
    var exercise: Exercise?=null
    var weight:String = ""
    var repetitions:String =""
    var notes:String=""



}