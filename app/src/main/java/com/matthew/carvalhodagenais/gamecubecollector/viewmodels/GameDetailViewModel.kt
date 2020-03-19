package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository
import kotlinx.coroutines.launch

class GameDetailViewModel(application: Application): AndroidViewModel(application) {

    private var selectedGame = MutableLiveData<Game>()
    private var repository = GameRepository(application)
    private var regionRepo = RegionRepository(application)
    private var conditionRepo = ConditionRepository(application)

    fun update(game: Game) = viewModelScope.launch {
        repository.updateGame(game)
    }

    fun delete(game: Game) = viewModelScope.launch {
        repository.deleteGame(game)
    }

    fun setSelectedGame(game: Game) {
        selectedGame.value = game
    }

    fun getSelectedGame(): Game? {
        return selectedGame.value
    }

    fun getRegionRepository(): RegionRepository = regionRepo

    fun getConditionRepository(): ConditionRepository = conditionRepo

    fun toggleFavourite() {
        selectedGame.value!!.isFavourite = !(selectedGame.value!!.isFavourite)!!
        update(selectedGame.value!!)
    }
}