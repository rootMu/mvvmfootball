package com.matthew.mvvmfootball.modules.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.matthew.mvvmfootball.R
import com.matthew.mvvmfootball.databinding.ItemEmptyBinding
import com.matthew.mvvmfootball.databinding.ItemPlayerBinding
import com.matthew.mvvmfootball.databinding.ItemTeamBinding
import com.matthew.mvvmfootball.databinding.ItemTitleBinding

class FootballAdapter :
    ListAdapter<ListUiModel, BaseViewHolder<*>>(DiffCallback()) {

    companion object {
        private const val TYPE_TITLE = 0
        private const val TYPE_PLAYER = 1
        private const val TYPE_TEAM = 2
        private const val TYPE_EMPTY = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_TITLE -> {
                TitleViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_title,
                        parent,
                        false
                    )
                )
            }
            TYPE_PLAYER -> {
                PlayerViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_player,
                        parent,
                        false
                    )
                )
            }
            TYPE_TEAM -> {
                TeamViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_team,
                        parent,
                        false
                    )
                )
            }
            TYPE_EMPTY -> {
                EmptyViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_empty,
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }



    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = getItem(position)
        when (holder) {
            is TitleViewHolder -> holder.bind(element as UiTitle)
            is PlayerViewHolder -> holder.bind(element as UiPlayer)
            is TeamViewHolder -> holder.bind(element as UiClub)
            is EmptyViewHolder -> holder.bind(element as UiEmptyResult)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiTitle -> TYPE_TITLE
            is UiPlayer -> TYPE_PLAYER
            is UiClub -> TYPE_TEAM
            is UiEmptyResult -> TYPE_EMPTY
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    inner class TitleViewHolder(private val binding: ItemTitleBinding) :
        BaseViewHolder<UiTitle>(binding.root) {
        override fun bind(item: UiTitle) {
            binding.title = item.name
        }
    }

    inner class PlayerViewHolder(private val binding: ItemPlayerBinding) :
        BaseViewHolder<UiPlayer>(binding.root) {
        override fun bind(item: UiPlayer) {
            binding.player = item
        }
    }

    inner class TeamViewHolder(private val binding: ItemTeamBinding) :
        BaseViewHolder<UiClub>(binding.root) {
        override fun bind(item: UiClub) {
            binding.club = item
        }
    }

    inner class EmptyViewHolder(private val binding: ItemEmptyBinding) :
        BaseViewHolder<UiEmptyResult>(binding.root) {
        override fun bind(item: UiEmptyResult) {
            binding.search = item.name
        }
    }

}

class DiffCallback<T : ListUiModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return when (oldItem) {
            is UiTitle -> oldItem.name == newItem.name
            is UiPlayer -> oldItem.name == newItem.name
            is UiClub -> oldItem.name == newItem.name
            is UiEmptyResult -> oldItem.name == newItem.name
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return when (oldItem) {
            is UiTitle -> oldItem == newItem
            is UiPlayer -> oldItem == newItem
            is UiClub -> oldItem == newItem
            is UiEmptyResult -> oldItem == newItem
            else -> false
        }
    }
}
