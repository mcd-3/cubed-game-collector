package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game

class GameListViewModel(application: Application): AndroidViewModel(application) {

    private var repository =
        GameRepository(application)

    fun getAllGames(): LiveData<List<Game>> {
        return repository.getAllGames()
    }

}