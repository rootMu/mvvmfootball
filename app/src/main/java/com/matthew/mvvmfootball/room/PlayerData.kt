package com.matthew.mvvmfootball.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playerData")
data class PlayerData(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "age") var age: String,
    @ColumnInfo(name = "club") var club: String,
    @ColumnInfo(name = "firstName") var firstName: String,
    @ColumnInfo(name = "secondName") var secondName: String,
    @ColumnInfo(name = "playerID") var playerID: String,
    @ColumnInfo(name = "nationality") var nationality: String,
    @ColumnInfo(name = "favourited") var favourited: Boolean = false
)