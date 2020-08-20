package com.matthew.mvvmfootball.dagger.modules

import android.content.Context
import androidx.room.Room
import com.matthew.mvvmfootball.dagger.qualifier.ForDatabase
import com.matthew.mvvmfootball.modules.ListRepository
import com.matthew.mvvmfootball.network.FootballApi
import com.matthew.mvvmfootball.room.FootballDao
import com.matthew.mvvmfootball.room.FootballDataHandler
import com.matthew.mvvmfootball.room.FootballDatabase
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Room Specific Dependencies
 */
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFootballDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,
        FootballDatabase::class.java, "football.db")
        .build()

    @Provides
    fun providesFootballDao(database: FootballDatabase) = database.footballDao()

    @Provides
    @Singleton
    @ForDatabase
    fun provideDatabaseExecutor(): Executor = Executors.newSingleThreadExecutor()

}