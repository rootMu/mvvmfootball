package com.matthew.mvvmfootball.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlayerData::class], version = 1)
abstract class FootballDatabase : RoomDatabase() {
    abstract fun footballDao(): FootballDao
}