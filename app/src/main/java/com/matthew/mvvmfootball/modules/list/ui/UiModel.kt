package com.matthew.mvvmfootball.modules.list.ui

import com.matthew.mvvmfootball.network.model.Player
import com.matthew.mvvmfootball.network.model.Team

data class UiModel(val players: List<Player>?, val teams: List<Team>?) {
    //combine two UiModel's
    fun add(that: UiModel?): UiModel? =
        this.let {
            return that?.apply {
                //take all values from right most ApiResponse
                //combine the players and teams lists so that the left most lists are first
                return UiModel(
                    players = players?.union(it.players ?: listOf())
                        ?.toList(),
                    teams = teams?.union(it.teams ?: listOf())?.toList()
                )
            } ?: it
        }
}
