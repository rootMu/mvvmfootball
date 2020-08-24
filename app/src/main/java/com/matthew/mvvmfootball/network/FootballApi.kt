package com.matthew.mvvmfootball.network

import com.matthew.mvvmfootball.network.model.ApiResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface FootballApi {

    /**
     * Get a list of football players and teams
     */
    @POST("/api/football/1.0/search")
    suspend fun getFootballData(@Query("searchString") searchString: String, @Query("searchType") searchType: String? = null, @Query("offset") offset: Int = 0): ApiResponse
}