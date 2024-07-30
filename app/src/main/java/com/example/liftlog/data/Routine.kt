package com.example.liftlog.data

import android.os.Build
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

class Routine:RealmObject {

    var _id:ObjectId = ObjectId()
    var name:String=""
    var note:String?=null
    var exercise:RealmList<Exercise> = realmListOf()
    var date: RealmInstant = RealmInstant.now()


}