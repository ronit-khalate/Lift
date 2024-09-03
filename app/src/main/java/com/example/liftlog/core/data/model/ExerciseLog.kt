package com.example.liftlog.core.data.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import org.mongodb.kbson.ObjectId

class ExerciseLog:EmbeddedRealmObject {


    var exercise:Exercise?=null
    var setList:RealmList<Set> = realmListOf()
}