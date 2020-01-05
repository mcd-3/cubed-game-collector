package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.matthew.carvalhodagenais.gamecubecollector.data.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game

class GameViewModel(application: Application): AndroidViewModel(application) {

    private var selectedGame = MutableLiveData<Game>()
    private var repository = GameRepository(application)

    fun insert(game: Game) {
        repository.insertGame(game)
    }

    fun delete(game: Game) {
        repository.deleteGame(game)
    }

    fun getAllGames(): LiveData<List<Game>> {
        return repository.getAllGames()
    }

    fun setSelectedGame(game: Game) {
        selectedGame.value = game
    }

    fun getSelectedGame(): Game? {
        return selectedGame.value
    }

    fun setFavourite(game: Game, isFavourite: Boolean) {
        game.isFavourite = isFavourite
    }
}