package com.matthew.mvvmfootball.modules

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.matthew.mvvmfootball.di.DaggerViewModelInjector
import com.matthew.mvvmfootball.di.NetworkModule
import com.matthew.mvvmfootball.di.ViewModelInjector
import com.matthew.mvvmfootball.network.FootballApi
import com.matthew.mvvmfootball.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.lang.Exception
import javax.inject.Inject

class ListViewModel : ViewModel(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TAG = "LIST_VIEW_MODEL"
    }

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    @Inject
    lateinit var mApi: FootballApi

    val viewState: MutableLiveData<UiModel> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val launchList = SingleLiveEvent<Void>()

    init {
        injector.inject(this)

        fetchDataFromServer(true)

        android.os.Handler().postDelayed({
            //launchList.call()
        }, 3000L)
    }

    override fun onRefresh() {
        fetchDataFromServer()
    }

    private fun fetchDataFromServer(firstCall: Boolean = false) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                loadingVisibility.postValue(View.VISIBLE)
                withTimeout(10000L) {
                    mApi.getListAsync("barc").await().body()?.let {



                        loadingVisibility.postValue(View.GONE)
                        if(firstCall)
                            launchList.call()
                    }
                }
            } catch (error: Exception) {
                loadingVisibility.postValue(View.GONE)
                error.handle()
            }
        }
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