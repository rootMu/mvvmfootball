package com.matthew.mvvmfootball.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("onRefreshListener")
fun setOnRefreshListener(view: SwipeRefreshLayout, listener: SwipeRefreshLayout.OnRefreshListener){
    view.setOnRefreshListener(listener)
}

@BindingAdapter("isRefreshing")
fun setIsRefreshing(view: SwipeRefreshLayout, visibility: MutableLiveData<Int>) {
    visibility.value?.let { value ->
        view.isRefreshing = value != View.GONE
    }
}