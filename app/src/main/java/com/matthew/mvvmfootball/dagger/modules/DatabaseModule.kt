package com.matthew.mvvmfootball.dagger.modules

import android.content.Context
import androidx.room.Room
import com.matthew.mvvmfootball.dagger.qualifier.ForDatabase
import com.matthew.mvvmfootball.data.local.FootballDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Room Specific Dependencies
 */
@InstallIn(ApplicationComponent::class)
@Module(includes = [NetworkModule::class])
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFootballDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context.applicationContext,
        FootballDatabase::class.java, "football.db")
        .build()

    @Provides
    fun providesFootballDao(database: FootballDatabase) = database.footballDao()

    @Provides
    @Singleton
    @ForDatabase
    fun provideDatabaseExecutor(): Executor = Executors.newSingleThreadExecutor()

}