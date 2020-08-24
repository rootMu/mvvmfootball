package com.matthew.mvvmfootball.ui.list.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.matthew.mvvmfootball.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.matthew.mvvmfootball.data.remote.FootballData
import com.matthew.mvvmfootball.data.repository.ListRepository
import com.matthew.mvvmfootball.ui.list.recyclerview.*
import com.matthew.mvvmfootball.utils.FlipableLiveData
import com.matthew.mvvmfootball.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*

class ListViewModel @ViewModelInject constructor(
    private var repository: ListRepository,
    @Assisted private val savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context,
    private var cm: ConnectivityManager
) : ViewModel(), SwipeRefreshLayout.OnRefreshListener, LifecycleObserver,
    SearchView.OnQueryTextListener {

    companion object {
        const val TAG = "LIST_VIEW_MODEL"
        private const val PLAYERS = "players"
        private const val TEAMS = "teams"
    }

    init {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                networkAvailability.postValue(false)
            }

            override fun onUnavailable() {
                networkAvailability.postValue(false)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {}

            override fun onAvailable(network: Network) {
                networkAvailability.postValue(true)
            }
        }

        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    private var loadMore: Boolean = false
    private var playerVisibility = FlipableLiveData(true)
    private var teamVisibility = FlipableLiveData(true)
    private val networkAvailability = MutableLiveData<Boolean>()
    val isNetworkAvailable: LiveData<Boolean> = networkAvailability
    private var timeAtLastCall = Calendar.getInstance().time

    val scrollToTopVisibility = MutableLiveData<Boolean>()
    val scrollToBottomVisibility = MutableLiveData<Boolean>()

    private val loadTrigger = MutableLiveData(Unit)

    private val _footballData = loadTrigger.switchMap {
        repository.getData(
            searchParameters.searchString?:"",
            searchType = searchParameters.searchType,
            offset = searchParameters.offset
        )
    }

    val footballDataLiveData: LiveData<Resource<FootballData>> = _footballData

    private val _uiData: MutableLiveData<List<UiModel>> = MutableLiveData()
    val uiData: LiveData<List<UiModel>> get() = _uiData

    private var searchParameters = SearchParameters()

    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            scrollToTopVisibility.postValue((recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() > 1)

            val bottom =
                (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            val adapterSize = (recyclerView.adapter?.itemCount ?: 1) - 1

            scrollToBottomVisibility.postValue(bottom != adapterSize)
        }
    }

    fun mapDataToUi(data: FootballData?) {

        val list = mutableListOf<UiModel>()

        data?.apply {
            players?.map {
                list.add(
                    UiPlayer(
                        name = "${it.firstName} ${it.secondName}",
                        age = it.age,
                        club = it.club,
                        nationality = it.nationality,
                        visibility = playerVisibility
                    )
                )
            }

            teams?.map {
                list.add(
                    UiClub(
                        name = it.name,
                        nationality = it.nationality,
                        homeGround = it.stadium,
                        city = it.city,
                        visibility = teamVisibility
                    )
                )
            }

            //if there were clubs add a club title to the beginning
            if (!teams.isNullOrEmpty()) {
                if (teams.size % 10 == 0 && !searchParameters.endOfTeamSearch)
                    list.add(
                        UiLoadMore(
                            onClick = loadTeams,
                            visibility = teamVisibility
                        )
                    )

                list.add(
                    players?.size ?: 0,
                    UiTitle(
                        context.getString(
                            R.string.clubs,
                            teams.size
                        ), ::showHideTeams
                    )
                )
            }

            //If there were players add a player title to the beginning
            if (!players.isNullOrEmpty()) {
                if (players.size % 10 == 0 && !searchParameters.endOfPlayerSearch)
                    list.add(
                        players.size,
                        UiLoadMore(
                            onClick = loadPlayers,
                            visibility = playerVisibility
                        )
                    )

                list.add(
                    0,
                    UiTitle(
                        context.getString(
                            R.string.players,
                            players.size
                        ), ::showHidePlayers
                    )
                )
            }

            if (players.isNullOrEmpty() && teams.isNullOrEmpty()) {
                list.add(
                    UiEmptyResult(
                        this@ListViewModel.searchParameters.searchString ?: ""
                    )
                )
            }
        }?: list.add(
            UiNetworkError()
        )

        loadingVisibility.postValue(View.GONE)

        _uiData.postValue(list)
    }

    fun mapErrorToUi(message: String?){
        loadingVisibility.postValue(View.GONE)
        val list = mutableListOf<UiModel>(
            UiNetworkError(
                name = message ?: ""
            )
        )
        _uiData.postValue(list)
    }

    private fun showHidePlayers() {
        playerVisibility.flip()
    }

    private fun showHideTeams() {
        teamVisibility.flip()
    }

    private val loadPlayers: () -> Unit = {
        Log.d(TAG, "Load More Players Clicked")
        searchParameters.endOfTeamSearch = false
        setSearchType(PLAYERS)
    }

    private val loadTeams: () -> Unit = {
        Log.d(TAG, "Load More Teams Clicked")
        searchParameters.endOfPlayerSearch = false
        setSearchType(TEAMS)
    }

    private fun setSearchType(type: String) {
        loadMore = true
        if (searchParameters.searchType == type) {
            searchParameters.offset += 10
        } else {
            searchParameters.searchType = type
            searchParameters.offset = 10
        }
        setSearchString(searchParameters.searchString)
    }

    lateinit var adapter: FootballAdapter

    fun getFootballAdapter() = adapter

    override fun onRefresh() {
        resetSearchParameters(searchParameters.searchString)
        setSearchString(searchParameters.searchString)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        loadMore = false
        resetSearchParameters()
        return setSearchString(query)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val now = Calendar.getInstance().time
        val diff = now.time.minus(timeAtLastCall.time)
        timeAtLastCall.time = now.time
        return if(diff > 400){
            onQueryTextSubmit(newText)
        }else {
            false
        }
    }

    private fun setSearchString(search: String? = null): Boolean {
        val newSearch = search ?: ""
        searchParameters.searchString = newSearch
        loadTrigger.value = Unit
        return !searchParameters.searchString.isNullOrEmpty()
    }

    private fun resetSearchParameters(search: String? = null) {
        searchParameters = SearchParameters(
            searchString = search,
            searchType = if (loadMore) searchParameters.searchType else null
        )
    }

    internal data class SearchParameters(
        var searchString: String? = "",
        var searchType: String? = null,
        var offset: Int = 0,
        var endOfPlayerSearch: Boolean = false,
        var endOfTeamSearch: Boolean = false
    )
}