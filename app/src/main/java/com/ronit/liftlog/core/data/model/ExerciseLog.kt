package com.ronit.liftlog.core.data.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class ExerciseLog:RealmObject {


    var _id:ObjectId = ObjectId()
    var exercise:Exercise? = Exercise()
    var setList:RealmList<Set> = realmListOf()
}