package com.matthew.carvalhodagenais.cubedcollector.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Game

@Dao
interface GameDao {

    @Insert
    suspend fun insert(game: Game)

    @Update
    suspend fun update(game: Game)

    @Delete
    suspend fun delete(game: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()

    @Query("SELECT * FROM game_table WHERE id = :id")
    fun getGameById(id: Int): LiveData<Game>

    @Query("SELECT * FROM game_table WHERE title = :title")
    fun getGamesByName(title: String): LiveData<List<Game>>

    @Query("SELECT * FROM game_table WHERE favourite = 1")
    fun getAllFavouriteGames(): LiveData<List<Game>>

    @Query("SELECT * FROM game_table")
    fun getAllGames(): LiveData<List<Game>>
}