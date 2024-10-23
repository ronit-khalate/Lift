package com.ronit.liftlog.core.data.model.entity

import androidx.compose.ui.Modifier
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class BodyWeight:RealmObject {

    var _id:ObjectId = ObjectId()
    var weight:Float = 0F
    var dateTime:Long = 0L
}