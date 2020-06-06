package com.matthew.carvalhodagenais.cubedcollector.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.cubedcollector.data.CollectorDatabase
import com.matthew.carvalhodagenais.cubedcollector.data.dao.GameDao
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Game
import com.matthew.carvalhodagenais.cubedcollector.helpers.ImageStorageHelper

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
        if (game.imageName != null) {
            ImageStorageHelper.deleteImage(ImageStorageHelper.IMAGE_PATH, game.imageName.toString())
        }
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