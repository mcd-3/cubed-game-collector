package com.matthew.carvalhodagenais.gamecubecollector.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.gamecubecollector.data.dao.GameDao
import com.matthew.carvalhodagenais.gamecubecollector.data.dao.RegionDao
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game

class GameRepository(application: Application) {

    private var gameDao: GameDao
    private var regionDao: RegionDao
    private var allGames: LiveData<List<Game>>

    init {
        val database: CollectorDatabase = CollectorDatabase.getInstance(application.applicationContext)!!
        gameDao = database.gameDao()
        regionDao = database.regionDao()
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

    fun getAllGames(): LiveData<List<Game>> {
        return allGames
    }

}