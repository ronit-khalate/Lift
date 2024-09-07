package com.example.liftlog.core.data.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import org.mongodb.kbson.ObjectId

class ExerciseLog:EmbeddedRealmObject {


    var _id:ObjectId = ObjectId()
    var exercise:Exercise? = Exercise()
    var setList:RealmList<Set> = realmListOf()
}