package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import kotlinx.coroutines.launch

class GameListViewModel(application: Application): AndroidViewModel(application) {

    private var repository =
        GameRepository(application)

    fun getAllGames(): LiveData<List<Game>> {
        return repository.getAllGames()
    }

    fun delete(game: Game) = viewModelScope.launch {  repository.deleteGame(game) }

}