package com.example.liftlog.data

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId
import java.util.Date

class Log: RealmObject {

    var _id:ObjectId = ObjectId()
    var routine:Routine? =null
    var date:RealmInstant = RealmInstant.now()
}