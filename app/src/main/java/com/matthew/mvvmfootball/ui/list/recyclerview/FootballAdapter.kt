package com.matthew.mvvmfootball.ui.list.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.matthew.mvvmfootball.R
import com.matthew.mvvmfootball.databinding.*

class FootballAdapter(private var lifecycleOwner: LifecycleOwner) :
    ListAdapter<UiModel, BaseViewHolder<*>>(DiffCallback()) {

    companion object {
        private const val TYPE_TITLE = 0
        private const val TYPE_PLAYER = 1
        private const val TYPE_TEAM = 2
        private const val TYPE_EMPTY = 3
        private const val TYPE_LOAD = 4
        private const val TYPE_NETWORK_ERROR = 5
    }

    private var players = 0
    private var teams = 0

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
                players++
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
                teams++
                TeamViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_team,
                        parent,
                        false
                    )
                )
            }
            TYPE_LOAD -> {
                LoadMoreViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_load,
                        parent,
                        false
                    )
                )
            }
            TYPE_NETWORK_ERROR -> {
                NetworkErrorViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_network_error,
                        parent,
                        false
                    )
                )
            }
            //Handle empty case in else, will catch all errors with an empty state
            else -> {
                EmptyViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_empty,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return if (getItemViewType(position) == TYPE_PLAYER && (getItem(position) as UiPlayer).visibility.value == true) {
            super.getItemId(position)
        } else {
            super.getItemId(position) - players
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = getItem(position)
        when (holder) {
            is TitleViewHolder -> holder.bind(element as UiTitle)
            is PlayerViewHolder -> holder.bind(element as UiPlayer)
            is TeamViewHolder -> holder.bind(element as UiClub)
            is EmptyViewHolder -> holder.bind(element as UiEmptyResult)
            is LoadMoreViewHolder -> holder.bind(element as UiLoadMore)
            is NetworkErrorViewHolder -> holder.bind(element as UiNetworkError)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiTitle -> TYPE_TITLE
            is UiPlayer -> TYPE_PLAYER
            is UiClub -> TYPE_TEAM
            is UiEmptyResult -> TYPE_EMPTY
            is UiLoadMore -> TYPE_LOAD
            is UiNetworkError -> TYPE_NETWORK_ERROR
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    inner class TitleViewHolder(private val binding: ItemTitleBinding) :
        BaseViewHolder<UiTitle>(binding.root) {
        override fun bind(item: UiTitle) {
            binding.title = item
            binding.showHide.setOnClickListener {
                item.onClick.invoke()
                binding.showHide.isSelected = !binding.showHide.isSelected
            }
        }
    }

    inner class PlayerViewHolder(private val binding: ItemPlayerBinding) :
        BaseViewHolder<UiPlayer>(binding.root) {
        override fun bind(item: UiPlayer) {
            binding.player = item
            binding.lifecycleOwner = lifecycleOwner
        }
    }

    inner class TeamViewHolder(private val binding: ItemTeamBinding) :
        BaseViewHolder<UiClub>(binding.root) {
        override fun bind(item: UiClub) {
            binding.club = item
            binding.lifecycleOwner = lifecycleOwner
        }
    }

    inner class EmptyViewHolder(private val binding: ItemEmptyBinding) :
        BaseViewHolder<UiEmptyResult>(binding.root) {
        override fun bind(item: UiEmptyResult) {
            binding.search = item.name
        }
    }

    inner class NetworkErrorViewHolder(private var binding: ItemNetworkErrorBinding) :
        BaseViewHolder<UiNetworkError>(binding.root) {
        override fun bind(item: UiNetworkError) {
            if(item.name.isNotEmpty()){
                binding.error = item.name
            }
        }
    }

    inner class LoadMoreViewHolder(private val binding: ItemLoadBinding) :
        BaseViewHolder<UiLoadMore>(binding.root) {
        override fun bind(item: UiLoadMore) {
            binding.load = item
            itemView.setOnClickListener {
                item.onClick.invoke()
            }
            binding.lifecycleOwner = lifecycleOwner
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<UiModel>() {
    override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return when (oldItem) {
            is UiTitle -> oldItem.name == newItem.name
            is UiPlayer -> oldItem.name == newItem.name
            is UiClub -> oldItem.name == newItem.name
            is UiEmptyResult -> oldItem.name == newItem.name
            is UiNetworkError -> oldItem.name == newItem.name
            is UiLoadMore -> oldItem.name == newItem.name
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        val areContentsTheSame = oldItem.equals(newItem)
        return areContentsTheSame
    }
}
