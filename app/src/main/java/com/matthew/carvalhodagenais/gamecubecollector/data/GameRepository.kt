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

    fun insertGame(game: Game) {
        val insert = InsertGameAsyncTask(gameDao).execute(game)
    }

    fun deleteGame(game: Game) {
        val delete = DeleteGameAsyncTask(gameDao).execute(game)
    }

    fun getAllGames(): LiveData<List<Game>> {
        return allGames
    }

    companion object {

        class InsertGameAsyncTask(gameDao: GameDao): AsyncTask<Game, Unit, Unit>() {
            val dao = gameDao
            override fun doInBackground(vararg params: Game?) {
                dao.insert(params[0]!!)
            }
        }

        class DeleteGameAsyncTask(gameDao: GameDao): AsyncTask<Game, Unit, Unit>() {
            val dao = gameDao
            override fun doInBackground(vararg params: Game?) {
                dao.delete(params[0]!!)
            }
        }

    }

}