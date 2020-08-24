package com.matthew.mvvmfootball.network.model


data class ApiResponse(
    val result: Result
) {
    //combine two ApiResponse's
    fun add(that: ApiResponse?): ApiResponse? =
        this.let {
            return that?.apply {
                //take all values from right most ApiResponse
                //combine the players and teams lists so that the left most lists are first
                return ApiResponse(
                    Result(
                        message = it.result.message?:"",
                        minVer = it.result.minVer?:"",
                        players = result.players?.union(it.result.players?: listOf())
                            ?.toList(),
                        request_order = it.result.request_order?:0,
                        searchString = it.result.searchString?:"",
                        searchType = it.result.searchType?:"",
                        serverAlert = it.result.serverAlert?:"",
                        status = it.result.status?:false,
                        teams = result.teams?.union(it.result.teams?: listOf())?.toList()
                    )
                )
            }?:it
        }
}

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