package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import kotlinx.coroutines.launch

class GameDetailViewModel(application: Application): AndroidViewModel(application) {

    private var selectedGame = MutableLiveData<Game>()
    private var repository = GameRepository(application)

    fun delete(game: Game) = viewModelScope.launch {
        repository.deleteGame(game)
    }

    fun setSelectedGame(game: Game) {
        selectedGame.value = game
    }

    fun getSelectedGame(): Game? {
        return selectedGame.value
    }

    fun getIsFavourite(): Boolean {
        return selectedGame.value!!.isFavourite!!
    }

    fun toggleFavourite() {
        selectedGame.value!!.isFavourite = !(selectedGame.value!!.isFavourite)!!
        update()
    }

    private fun update() = viewModelScope.launch {
        repository.updateGame(selectedGame.value!!)
    }
}