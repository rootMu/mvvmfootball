package com.matthew.mvvmfootball.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.matthew.mvvmfootball.modules.list.ui.UiModel

@Dao
interface FootballDao {
    //Players
    @Query("SELECT * from playerData")
    fun getAllPlayers(): LiveData<List<PlayerData>>

    @Insert(onConflict = IGNORE)
    fun insertPlayer(player: PlayerData)

    @Insert(onConflict = IGNORE)
    fun insertPlayers(players: List<PlayerData>)

    @Delete
    fun delete(player: PlayerData)

    @Delete
    fun deletePlayers(vararg players: PlayerData)

    @Query("DELETE from playerData")
    fun deleteAllPlayers()

    //Teams
    @Query("SELECT * from teamData")
    fun getAllTeams(): LiveData<List<TeamData>>

    @Insert(onConflict = IGNORE)
    fun insertTeam(team: TeamData)

    @Insert(onConflict = IGNORE)
    fun insertTeams(teams: List<TeamData>)

    @Delete
    fun delete(team: TeamData)

    @Delete
    fun deleteTeams(vararg teams: TeamData)

    @Query("DELETE from teamData")
    fun deleteAllTeams()
}