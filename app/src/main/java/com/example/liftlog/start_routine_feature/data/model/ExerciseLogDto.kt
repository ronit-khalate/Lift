package com.example.liftlog.start_routine_feature.data.model

import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.data.model.Set
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList

data class ExerciseLogDto(

    var exerciseID: String? = null,
    var setList: RealmList<Set> = realmListOf()
)
