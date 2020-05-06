package com.matthew.carvalhodagenais.cubedcollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.cubedcollector.data.repositories.GameRepository
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Game
import kotlinx.coroutines.launch

class GameListViewModel(application: Application): AndroidViewModel(application) {

    private var repository =
        GameRepository(application)

    fun getAllGames(): LiveData<List<Game>> {
        return repository.getAllGames()
    }

    fun getAllFavouriteGames(): LiveData<List<Game>> {
        return repository.getAllFavouriteGames()
    }

    fun delete(game: Game) = viewModelScope.launch {  repository.deleteGame(game) }

}