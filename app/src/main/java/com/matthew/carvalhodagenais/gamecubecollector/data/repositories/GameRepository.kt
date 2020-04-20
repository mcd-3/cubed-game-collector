package com.matthew.carvalhodagenais.gamecubecollector.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.gamecubecollector.data.CollectorDatabase
import com.matthew.carvalhodagenais.gamecubecollector.data.dao.GameDao
import com.matthew.carvalhodagenais.gamecubecollector.data.dao.RegionDao
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game

class GameRepository(application: Application) {

    private var gameDao: GameDao
    private var allGames: LiveData<List<Game>>

    init {
        val database: CollectorDatabase = CollectorDatabase.getInstance(
            application.applicationContext
        )!!
        gameDao = database.gameDao()
        allGames = gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insert(game)
    }

    suspend fun updateGame(game: Game) {
        gameDao.update(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDao.delete(game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    fun getGameById(id: Int): LiveData<Game> {
        return gameDao.getGameById(id)
    }

    fun getAllGames(): LiveData<List<Game>> {
        return allGames
    }

    fun getAllFavouriteGames(): LiveData<List<Game>> {
        return gameDao.getAllFavouriteGames()
    }

}