package com.matthew.mvvmfootball.network.model


data class ApiResponse(
    val result: Result
)

data class Result(
    val message: String,
    val minVer: String,
    val players: List<Player>?,
    val request_order: Int,
    val searchString: String?,
    val searchType: String,
    val serverAlert: String,
    val status: Boolean,
    val teams: List<Team>?
)

data class Player(
    val playerAge: String,
    val playerClub: String,
    val playerFirstName: String,
    val playerID: String,
    val playerNationality: String,
    val playerSecondName: String
)

data class Team(
    val isNation: String,
    val teamCity: String,
    val teamID: String,
    val teamName: String,
    val teamNationality: String,
    val teamStadium: String
)