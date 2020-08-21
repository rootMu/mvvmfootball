package com.matthew.mvvmfootball.modules

import android.view.View
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.matthew.mvvmfootball.network.model.ApiResponse
import com.matthew.mvvmfootball.room.PlayerData
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

    private fun mapDataToPlayers(data: ApiResponse) = data.result.players.map {
        PlayerData(
            0,
            it.playerAge,
            it.playerClub,
            it.playerFirstName,
            it.playerSecondName,
            it.playerID,
            it.playerNationality
        )
    }

    private fun mapDataToTeams(data: ApiResponse) = data.result.teams

    private fun loadData() =
        liveData(Dispatchers.IO) {
            val retrievedData = repository.getData("barc")
            loadingVisibility.postValue(View.GONE)
            emit(retrievedData)
        }

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