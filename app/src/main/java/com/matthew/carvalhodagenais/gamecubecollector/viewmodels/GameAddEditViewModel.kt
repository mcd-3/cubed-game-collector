package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import kotlinx.coroutines.launch

class GameAddEditViewModel(application: Application): AndroidViewModel(application) {

    private var selectedGame = MutableLiveData<Game>()
    private var repository = GameRepository(application)

    fun insert(game: Game) = viewModelScope.launch {
        repository.insertGame(game)
    }

    fun update(game: Game) = viewModelScope.launch {
        repository.updateGame(game)
    }

    fun setSelectedGame(game: Game) {
        selectedGame.value = game
    }

    fun getSelectedGame(): Game? {
        return selectedGame.value
    }

}