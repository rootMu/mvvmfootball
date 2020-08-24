package com.matthew.mvvmfootball.modules

import androidx.annotation.NonNull
import androidx.lifecycle.*
import com.matthew.mvvmfootball.dagger.qualifier.ForDatabase
import com.matthew.mvvmfootball.modules.list.ui.UiModel
import com.matthew.mvvmfootball.network.FootballApi
import com.matthew.mvvmfootball.network.model.ApiResponse
import com.matthew.mvvmfootball.network.model.Player
import com.matthew.mvvmfootball.network.model.Team
import com.matthew.mvvmfootball.room.FootballDao
import com.matthew.mvvmfootball.room.PlayerData
import com.matthew.mvvmfootball.room.TeamData
import com.matthew.mvvmfootball.utils.performGetOperation
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.Executor
import javax.annotation.Resource
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Result.Companion.success

@Singleton
class ListRepository @Inject constructor(
    private val footballApi: FootballApi,
    private val footballDao: FootballDao,
    @ForDatabase @NonNull private val databaseExecutor: Executor
) {

    fun getData(search: String?,
                searchType: String? = null) = performGetOperation(
        databaseQuery = {
            MediatorLiveData<UiModel>().apply {
                addSource(Transformations.map(footballDao.getAllPlayers()) { playerData ->
                    playerData.map {
                        Player(
                            it.age,
                            it.club,
                            it.firstName,
                            it.playerID,
                            it.nationality,
                            it.secondName
                        )
                    }
                }) { players ->
                    value = UiModel(players, value?.teams)
                }
                addSource(Transformations.map(footballDao.getAllTeams()) { teamData ->
                    teamData.map {
                        Team(
                            it.isNation,
                            it.teamCity,
                            it.teamID,
                            it.teamName,
                            it.teamNationality,
                            it.teamStadium
                        )
                    }
                }) { teams ->
                    value = UiModel(value?.players, teams)
                }
            }
        },
        networkCall = { getResult { footballApi.getFootballData(search ?: "", searchType, 9)  }},
        saveCallResult = { localDataSource.insertAll(it.results) }
    )


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