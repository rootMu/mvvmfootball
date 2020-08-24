package com.matthew.mvvmfootball.utils

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.matthew.mvvmfootball.R
import com.matthew.mvvmfootball.ui.list.recyclerview.FootballAdapter
import com.matthew.mvvmfootball.ui.list.recyclerview.UiModel

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
fun setAdapterItems(view: RecyclerView, items: LiveData<List<UiModel>>) {
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
        when (club) {
            "Retired" -> club
            "Free" -> club
            "Deceased" -> club
            else -> view.resources.getString(R.string.plays_for, club)
        }
}

@BindingAdapter("adjustHeight")
fun adjustHeight(view: View, visibility: LiveData<Boolean>){
    view.layoutParams.height = visibility.value?.let{
        if(it)
            ViewGroup.LayoutParams.WRAP_CONTENT
        else
            0
    }?:0
}

//android:src="@{context.getResources().getIdentifier(FlagName.getInstance().getFlagFilenameForNationality(player.nationality, context),String.format(@string/drawable),context.getPackageName())}"
@BindingAdapter("setFlagForPlayer")
fun setFlagForPlayer(view: ImageView, nationality: String){
    val filename = FlagName.getFlagFilenameForNationality(nationality, view.context)
    view.setImageResource( view.context.resources.getIdentifier(filename, "drawable", view.context.packageName))
}

@BindingAdapter("setFlagForTeam")
fun setFlagForTeam(view: ImageView, country: String){
    val filename = FlagName.getFlagFilenameForCountry(country, view.context)
    view.setImageResource( view.context.resources.getIdentifier(filename, "drawable", view.context.packageName))
}