package com.matthew.mvvmfootball.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlayerData::class, TeamData::class], version = 1, exportSchema = false)
abstract class FootballDatabase : RoomDatabase() {
    abstract fun footballDao(): FootballDao
}