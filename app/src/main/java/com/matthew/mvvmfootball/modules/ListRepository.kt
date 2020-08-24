package com.matthew.mvvmfootball.modules

import androidx.annotation.NonNull
import com.matthew.mvvmfootball.dagger.qualifier.ForDatabase
import com.matthew.mvvmfootball.modules.list.ui.UiModel
import com.matthew.mvvmfootball.network.FootballApi
import com.matthew.mvvmfootball.network.model.ApiResponse
import com.matthew.mvvmfootball.room.FootballDao
import com.matthew.mvvmfootball.room.PlayerData
import com.matthew.mvvmfootball.room.TeamData
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject constructor(
    private val footballApi: FootballApi,
    private val footballDao: FootballDao,
    @ForDatabase @NonNull private val databaseExecutor: Executor
) {

    suspend fun getData(search: String?, searchType: String? = null, offset: Int = 0): UiModel? {
        return insertDataIntoDatabase(getDataFromServer(search, searchType, offset))
    }

    private suspend fun getDataFromServer(
        search: String?,
        searchType: String? = null,
        offset: Int = 0
    ) = footballApi.getFootballData(search ?: "", searchType, offset)

    private fun insertDataIntoDatabase(apiResponse: ApiResponse): UiModel {
        databaseExecutor.execute {
            apiResponse.result.players?.map {
                PlayerData(
                    0,
                    it.playerAge,
                    it.playerClub,
                    it.playerFirstName,
                    it.playerSecondName,
                    it.playerID,
                    it.playerNationality,
                    false
                ).apply {
                    footballDao.insertPlayer(this)
                }

            }
            apiResponse.result.teams?.map {
                TeamData(
                    0,
                    it.isNation,
                    it.teamCity,
                    it.teamID,
                    it.teamName,
                    it.teamNationality,
                    it.teamStadium,
                    false
                ).apply {
                    footballDao.insertTeam(this)
                }
            }
        }
        return UiModel(apiResponse.result.players, apiResponse.result.teams)
    }

}