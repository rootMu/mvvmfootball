package com.matthew.mvvmfootball.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teamData")
data class TeamData(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "isNation") var isNation: String,
    @ColumnInfo(name = "teamCity") var teamCity: String,
    @ColumnInfo(name = "teamID") var teamID: String,
    @ColumnInfo(name = "teamName") var teamName: String,
    @ColumnInfo(name = "teamNationality") var teamNationality: String,
    @ColumnInfo(name = "teamStadium") var teamStadium: String,
    @ColumnInfo(name = "favourited") var favourited: Boolean = false
)