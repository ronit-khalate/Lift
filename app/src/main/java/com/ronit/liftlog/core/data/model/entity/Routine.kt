package com.ronit.liftlog.core.data.model.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class Routine:RealmObject {

    var _id:ObjectId = ObjectId()
    var name:String=""
    var note:String=""
    var exercise:RealmList<Exercise> = realmListOf()
    var date: RealmInstant = RealmInstant.now()


}