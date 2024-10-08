package com.ronit.liftlog.core.di

import com.ronit.liftlog.core.data.remote.ExerciseListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @GitHubPageBaseUrl
    fun provideBaseUrl():String = "https://ronit-khalate.github.io/exercise-db/"


    @Provides
    @Singleton
    fun provideRetrofit(@GitHubPageBaseUrl baseUrl:String):Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun provideExerciseService(retrofit: Retrofit):ExerciseListService = retrofit.create(ExerciseListService::class.java)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GitHubPageBaseUrl

