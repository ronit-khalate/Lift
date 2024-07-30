package com.example.liftlog.di

import com.example.liftlog.data.Exercise
import com.example.liftlog.data.Log
import com.example.liftlog.data.Routine
import com.example.liftlog.data.Set
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
            schema = setOf(Set::class,Exercise::class,Routine::class,Log::class)
        )

        return Realm.open(config)

    }
}