package com.matthew.mvvmfootball.modules

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.matthew.mvvmfootball.room.FootballDataHandler
import com.matthew.mvvmfootball.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class ListViewModel @Inject constructor(private val footballDataHandler: FootballDataHandler): ViewModel(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TAG = "LIST_VIEW_MODEL"
    }

    val viewState: MutableLiveData<UiModel> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val launchList = SingleLiveEvent<Void>()

    init {
        fetchDataFromServer(true)
    }

    override fun onRefresh() {
        fetchDataFromServer()
    }

    private fun fetchDataFromServer(firstCall: Boolean = false) {
        viewModelScope.launch {
            with(Dispatchers.IO){
                //repository.retriveDataFromServer("barc")
            }
        }


//        viewModelScope.launch(Dispatchers.Main) {
//            try {
//                loadingVisibility.postValue(View.VISIBLE)
//                withTimeout(10000L) {
//                    mApi.getListAsync("barc").await().body()?.let {
//
//
//
//                        loadingVisibility.postValue(View.GONE)
//                        if(firstCall)
//                            launchList.call()
//                    }
//                }
//            } catch (error: Exception) {
//                loadingVisibility.postValue(View.GONE)
//                error.handle()
//            }
//        }
    }

    private fun Exception.handle() {
        when (this) {
            is TimeoutCancellationException -> {
                Log.e(TAG, "Search Timed out", this)
            }
            else -> {
                Log.e(TAG, "API Call failed", this)
            }
        }

        viewState.postValue(UiModel.Error(this))
    }


    sealed class UiModel {
        data class Error(val exception: Exception) : UiModel()
    }

}