package com.example.liftlog.core.data.model


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class Set:RealmObject {

    @PrimaryKey
    var _id:ObjectId = ObjectId()
    var weight:Float = 0.0f
    var repetitons:Int =0
}