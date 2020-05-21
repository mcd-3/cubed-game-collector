package com.matthew.carvalhodagenais.cubedcollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Game
import com.matthew.carvalhodagenais.cubedcollector.data.repositories.AccessoryRepository
import com.matthew.carvalhodagenais.cubedcollector.data.repositories.ConsoleRepository
import com.matthew.carvalhodagenais.cubedcollector.data.repositories.GameRepository
import com.matthew.carvalhodagenais.cubedcollector.helpers.CSVFileStorageHelper
import com.matthew.carvalhodagenais.cubedcollector.helpers.ImageStorageHelper
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application): AndroidViewModel(application) {

    private val gameRepo: GameRepository = GameRepository(application)
    private val consoleRepo: ConsoleRepository = ConsoleRepository(application)
    private val accessoryRepo: AccessoryRepository = AccessoryRepository(application)

    fun getAllGames(): LiveData<List<Game>> {
        return gameRepo.getAllGames()
    }

    fun exportData(gameList: List<Game>?) = viewModelScope.launch {
        CSVFileStorageHelper.exportToCSV(gameList)
    }

    fun deleteAllData() = viewModelScope.launch {
        gameRepo.deleteAllGames()
        consoleRepo.deleteAllConsoles()
        accessoryRepo.deleteAllAccessories()
        ImageStorageHelper.deleteAllImages()
    }

}