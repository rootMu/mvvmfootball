package com.matthew.mvvmfootball.modules

import androidx.annotation.NonNull
import com.matthew.mvvmfootball.dagger.qualifier.ForDatabase
import com.matthew.mvvmfootball.network.FootballApi
import com.matthew.mvvmfootball.network.model.ApiResponse
import com.matthew.mvvmfootball.room.FootballDao
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject constructor(private val footballApi: FootballApi,
                                         private val footballDao: FootballDao,
                                         @ForDatabase @NonNull private val databaseExecutor: Executor) {

    suspend fun getData(search: String?, searchType: String? = null, offset: Int = 0): ApiResponse = footballApi.getFootballData(search?:"", searchType, offset)

}