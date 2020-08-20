package com.matthew.mvvmfootball.network

import com.matthew.mvvmfootball.network.model.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FootballApi {

    /**
     * Get a list of football players and teams
     */
    @POST("/api/football/1.0/search")
    fun getListAsync(@Query("searchString") searchString: String): ApiRequestInterface<ApiResponse>
}