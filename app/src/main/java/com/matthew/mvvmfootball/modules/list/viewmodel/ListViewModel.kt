package com.matthew.mvvmfootball.modules.list.viewmodel

import android.view.View
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.matthew.mvvmfootball.modules.ListRepository
import com.matthew.mvvmfootball.modules.list.ui.*
import com.matthew.mvvmfootball.network.model.ApiResponse
import kotlinx.coroutines.Dispatchers

class ListViewModel @ViewModelInject constructor(
    private var repository: ListRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), SwipeRefreshLayout.OnRefreshListener, LifecycleObserver {

    companion object {
        const val TAG = "LIST_VIEW_MODEL"
    }

    val viewState: MutableLiveData<UiModel> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    private val loadTrigger = MutableLiveData(Unit)

    val footballLiveData: LiveData<ApiResponse> = loadTrigger.switchMap {
        loadData()
    }

    val playerData = Transformations.map(footballLiveData, ::mapDataToPlayers)
    val teamData = Transformations.map(footballLiveData, ::mapDataToTeams)
    val uiData = Transformations.map(footballLiveData, ::mapDataToUi)

    private fun mapDataToPlayers(data: ApiResponse) = data.result.players
    private fun mapDataToTeams(data: ApiResponse) = data.result.teams
    private fun mapDataToUi(data: ApiResponse): List<ListUiModel> {

        val list = mutableListOf<ListUiModel>()

        data.result.apply {

            players.map {
                list.add(
                    UiPlayer(
                        name = "${it.playerFirstName} ${it.playerSecondName}",
                        nationality = it.playerNationality,
                        club = it.playerClub
                    )
                )
            }

            teams.map {
                list.add(
                    UiClub(
                        name = it.teamName,
                        nationality = it.teamNationality,
                        homeGround = it.teamStadium,
                        city = it.teamCity
                    )
                )
            }

            //if there were clubs add a club title to the beginning
            if (players.isNotEmpty()) {
                list.add(players.size, UiTitle("Clubs"))
            }

            //If there were players add a player title to the beginning
            if (players.isNotEmpty()) {
                list.add(0, UiTitle("Players"))
            }

        }
        return list
    }

    private fun loadData() =
        liveData(Dispatchers.IO) {
            val retrievedData = repository.getData("barc")
            loadingVisibility.postValue(View.GONE)
            emit(retrievedData)
        }

    private val adapter =
        FootballAdapter()

    fun getFootballAdapter() = adapter

    init {
        onRefresh()
    }

    override fun onRefresh() {
        loadTrigger.value = Unit
    }

    sealed class UiModel {
        data class Error(val exception: Exception) : UiModel()
    }

}