package com.matthew.mvvmfootball.room

import androidx.annotation.NonNull
import com.matthew.mvvmfootball.dagger.qualifier.ForDatabase
import com.matthew.mvvmfootball.network.FootballApi
import com.matthew.mvvmfootball.network.model.ApiResponse
import java.lang.Exception
import java.util.concurrent.Executor
import javax.inject.Inject
import com.matthew.mvvmfootball.network.Result as LocalResult

class FootballDataHandler @Inject constructor(
    private val footballDao: FootballDao,
    private val footballApi: FootballApi,
    @ForDatabase @NonNull private val databaseExecutor: Executor
) {

    fun retrieveDataFromServer(string: String) {

        val call = footballApi.getListAsync(string)

        try {
            with(call.performRequest()) {
                handle()
            }
        } catch (e: Exception)  {
            handleError(e)
        }
    }

    private fun LocalResult<ApiResponse>.handle() {
        when (this) {
            is LocalResult.Success -> handleSuccess(result)
            is LocalResult.Error -> handleError(exception)
        }
    }

    private fun handleSuccess(success: ApiResponse) {
        val players = success.result.players.map {
            PlayerData(0,
                it.playerAge,
                it.playerClub,
                it.playerFirstName,
                it.playerSecondName,
                it.playerID,
                it.playerNationality)
        }
        databaseExecutor.execute {
            footballDao.insertPlayers(players)
        }
    }

    private fun handleError(exception: Exception) {}
}