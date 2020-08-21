package com.matthew.mvvmfootball.modules

import com.matthew.mvvmfootball.network.FootballApi
import com.matthew.mvvmfootball.network.model.ApiResponse
import com.matthew.mvvmfootball.room.FootballDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject constructor(private val footballApi: FootballApi,
                                         private val footballDao: FootballDao) {

    suspend fun getData(search: String): ApiResponse = footballApi.getFootballData(search)

}