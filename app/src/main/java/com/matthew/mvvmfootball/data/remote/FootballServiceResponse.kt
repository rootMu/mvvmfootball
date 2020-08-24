package com.matthew.mvvmfootball.data.remote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class FootballData(
    val result: Result
)

data class Result(
    val message: String="",
    val minVer: String="",
    @SerializedName("players")
    val players: List<PlayerData>?,
    val request_order: Int =0,
    val searchString: String?=null,
    val searchType: String="",
    val serverAlert: String="",
    val status: Boolean = false,
    @SerializedName("teams")
    val teams: List<TeamData>?
)

@Entity(tableName = "playerData")
data class PlayerData(
    @ColumnInfo(name = "age")
    @SerializedName("playerAge")
    val age: String,
    @ColumnInfo(name = "club")
    @SerializedName("playerClub")
    val club: String,
    @ColumnInfo(name = "firstName")
    @SerializedName("playerFirstName")
    val firstName: String,
    @ColumnInfo(name = "playerID")
    val playerID: String,
    @ColumnInfo(name = "nationality")
    @SerializedName("playerNationality")
    val nationality: String,
    @ColumnInfo(name = "secondName")
    @SerializedName("playerSecondName")
    val secondName: String,
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "favourited") var favourited: Boolean = false
)

@Entity(tableName = "teamData")
data class TeamData(
    @ColumnInfo(name = "isNation")
    val isNation: String,
    @ColumnInfo(name = "city")
    @SerializedName("teamCity")
    val city: String,
    @ColumnInfo(name = "teamID")
    val teamID: String,
    @ColumnInfo(name = "name")
    @SerializedName("teamName")
    val name: String,
    @ColumnInfo(name = "nationality")
    @SerializedName("teamNationality")
    val nationality: String,
    @ColumnInfo(name = "stadium")
    @SerializedName("teamStadium")
    val stadium: String,
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "favourited") var favourited: Boolean = false
)