package com.ronit.liftlog.core.data.model.entity


import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class Set:EmbeddedRealmObject {

    
    var _id:ObjectId = ObjectId()
    var setNo:Int = 0
    var exerciseId: ObjectId?=null
    var weight:String = ""
    var repetitions:String =""
    var notes:String=""

    var previousWeight:String=""
    var previousRepetitions:String=""
    var previousNote:String =""



    fun copyData(
        weight:String = this.weight ,
        repetitions:String=this.repetitions,
        notes:String = this.notes
    ):Set{

        return Set().apply {

            this._id = this@Set._id
            this.setNo = this@Set.setNo
            this.exerciseId=this@Set.exerciseId
            this.weight =weight
            this.repetitions = repetitions
            this.notes = notes
            this.previousWeight = this@Set.previousWeight
            this.previousRepetitions = this@Set.previousRepetitions
            this.previousNote = this@Set.notes
        }
    }


    fun isEmpty():Boolean{


        return this.repetitions.isNotBlank() || this.weight.isNotBlank() || this.notes.isNotBlank()
    }



}