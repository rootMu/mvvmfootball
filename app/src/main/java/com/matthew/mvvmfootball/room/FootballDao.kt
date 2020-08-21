package com.matthew.mvvmfootball.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface FootballDao {
    @Query("SELECT * from playerData")
    fun getAllPlayers(): List<PlayerData>

    @Insert(onConflict = REPLACE)
    fun insertPlayer(player: PlayerData)

    @Insert(onConflict = REPLACE)
    fun insertPlayers(players: List<PlayerData>)

    @Delete
    fun delete(player: PlayerData)

    @Delete
    fun deletePlayers(vararg players: PlayerData)

    @Query("DELETE from playerData")
    fun deleteAll()
}