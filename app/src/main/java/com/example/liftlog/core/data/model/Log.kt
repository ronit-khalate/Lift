package com.example.liftlog.core.data.model

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class Log: RealmObject {

    var _id:ObjectId = ObjectId()
    var routine: Routine? =null
    var date:RealmInstant = RealmInstant.now()
}