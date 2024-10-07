package com.ronit.liftlog.core.di

import com.ronit.liftlog.core.data.model.Exercise
import com.ronit.liftlog.core.data.model.Log
import com.ronit.liftlog.core.data.model.Routine
import com.ronit.liftlog.core.data.model.ExerciseLog
import com.ronit.liftlog.core.data.model.Set
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideRealm():Realm {
        val config = RealmConfiguration.create(
            schema = setOf(Set::class, Exercise::class, Routine::class, Log::class,ExerciseLog::class)
        )

        return Realm.open(config)

    }
}