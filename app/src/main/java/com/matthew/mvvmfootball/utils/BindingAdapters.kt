package com.matthew.mvvmfootball.utils

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.matthew.mvvmfootball.R
import com.matthew.mvvmfootball.modules.list.ui.FootballAdapter
import com.matthew.mvvmfootball.modules.list.ui.ListUiModel

@BindingAdapter("onRefreshListener")
fun setOnRefreshListener(view: SwipeRefreshLayout, listener: SwipeRefreshLayout.OnRefreshListener) {
    view.setOnRefreshListener(listener)
}

@BindingAdapter("isRefreshing")
fun setIsRefreshing(view: SwipeRefreshLayout, visibility: MutableLiveData<Int>) {
    visibility.value?.let { value ->
        view.isRefreshing = value != View.GONE
    }
}

@BindingAdapter("submitList")
fun setAdapterItems(view: RecyclerView, items: LiveData<List<ListUiModel>>) {
    items.value?.let {
        with(view.adapter as FootballAdapter) {
            submitList(it)
        }
    }
}

@BindingAdapter("setPlayerTeam")
fun setPlayerTeam(view: TextView, club: String) {

    view.visibility =
        if (club == "Unknown") {
            View.GONE
        } else {
            View.VISIBLE
        }

    view.text =
        if (club == "Retired") {
            club
        } else {
            view.resources.getString(R.string.plays_for, club)
        }
}